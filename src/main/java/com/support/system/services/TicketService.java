package com.support.system.services;

import com.support.system.dto.TicketCreateDTO;
import com.support.system.dto.TicketResponseDTO;
import com.support.system.dto.TicketUpdateStatusDTO;
import com.support.system.entities.Ticket;
import com.support.system.entities.User;
import com.support.system.enums.TicketStatus;
import com.support.system.exceptions.ResourceNotFoundException;
import com.support.system.repositories.TicketRepository;
import com.support.system.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // Recupera o usuário logado a partir do contexto de segurança
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    // Cria um novo chamado associado ao usuário logado
    public TicketResponseDTO create(TicketCreateDTO dto) {
        User current = getCurrentUser();

        Ticket t = new Ticket();
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setPriority(dto.getPriority());
        t.setStatus(TicketStatus.OPEN);
        t.setCreatedBy(current);

        ticketRepository.save(t);
        return toResponse(t);
    }

    // Lista os chamados do usuário logado
    public List<TicketResponseDTO> findMyTickets() {
        User current = getCurrentUser();
        return ticketRepository.findByCreatedById(current.getId())
                .stream().map(this::toResponse).toList();
    }

    // Lista todos os chamados (uso de admin)
    public List<TicketResponseDTO> findAllTickets() {
        return ticketRepository.findAll().stream().map(this::toResponse).toList();
    }

    // Busca um chamado específico
    public TicketResponseDTO findById(Long id) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        return toResponse(t);
    }

    // Atualiza um chamado se for o dono
    public TicketResponseDTO updateTicket(Long id, TicketCreateDTO dto) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));

        User current = getCurrentUser();
        if (!t.getCreatedBy().getId().equals(current.getId())) {
            throw new RuntimeException("Não autorizado a editar este chamado");
        }

        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setPriority(dto.getPriority());
        ticketRepository.save(t);
        return toResponse(t);
    }

    // Permite alterar apenas o status do chamado (uso de admin)
    public TicketResponseDTO changeStatus(Long id, TicketUpdateStatusDTO dto) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        t.setStatus(dto.getStatus());
        ticketRepository.save(t);
        return toResponse(t);
    }

    // Atribui um chamado a um usuário (técnico)
    public TicketResponseDTO assignTo(Long ticketId, Long userId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        User assignee = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        t.setAssignedTo(assignee);
        ticketRepository.save(t);
        return toResponse(t);
    }

    // Converte entidade para DTO de resposta
    private TicketResponseDTO toResponse(Ticket t) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setDescription(t.getDescription());
        dto.setStatus(t.getStatus());
        dto.setPriority(t.getPriority());
        dto.setCreatedBy(t.getCreatedBy() != null ? t.getCreatedBy().getName() : null);
        dto.setAssignedTo(t.getAssignedTo() != null ? t.getAssignedTo().getName() : null);
        dto.setCreatedAt(t.getCreatedAt());
        dto.setUpdatedAt(t.getUpdatedAt());
        return dto;
    }
}
