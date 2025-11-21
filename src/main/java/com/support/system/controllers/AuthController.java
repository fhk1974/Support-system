package com.support.system.controllers;

import com.support.system.dto.*;
import com.support.system.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint para registrar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody UserRegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok(new ApiResponse<>("ok", "Usuário registrado com sucesso", null));
    }

    // Endpoint de login que devolve o token JWT
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody UserLoginDTO dto) {
        AuthResponseDTO auth = authService.login(dto);
        return ResponseEntity.ok(new ApiResponse<>("ok", "Login realizado com sucesso", auth));
    }
}
