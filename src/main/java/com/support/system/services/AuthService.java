package com.support.system.services;

import com.support.system.dto.AuthResponseDTO;
import com.support.system.dto.UserLoginDTO;
import com.support.system.dto.UserRegisterDTO;
import com.support.system.entities.User;
import com.support.system.enums.Role;
import com.support.system.exceptions.ResourceNotFoundException;
import com.support.system.repositories.UserRepository;
import com.support.system.security.jwt.JwtUtil;
import com.support.system.vo.ContactInfoVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    // Registra um novo usuário no sistema
    public void register(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.USER);
        user.setContactInfo(new ContactInfoVO(dto.getPhone(), dto.getDepartment()));

        userRepository.save(user);
    }

    // Realiza login e devolve um token JWT
    public AuthResponseDTO login(UserLoginDTO dto) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authManager.authenticate(authToken);
        } catch (Exception e) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                );

        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponseDTO(token);
    }
}
