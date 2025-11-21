package com.support.system.controllers;

import com.support.system.dto.*;
import com.support.system.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Cria um novo chamado para o usuário logado
    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponseDTO>> create(@Valid @RequestBody TicketCreateDTO dto) {
        TicketResponseDTO ticket = ticketService.create(dto);
        return ResponseEntity.ok(new ApiResponse<>("ok", "Chamado criado", ticket));
    }

    // Lista os chamados do usuário logado
    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponseDTO>>> myTickets() {
        List<TicketResponseDTO> tickets = ticketService.findMyTickets();
        return ResponseEntity.ok(new ApiResponse<>("ok", "Meus chamados", tickets));
    }

    // Lista todos os chamados (apenas ADMIN)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<TicketResponseDTO>>> allTickets() {
        List<TicketResponseDTO> tickets = ticketService.findAllTickets();
        return ResponseEntity.ok(new ApiResponse<>("ok", "Todos os chamados", tickets));
    }

    // Busca um chamado específico
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> findById(@PathVariable Long id) {
        TicketResponseDTO ticket = ticketService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("ok", "Chamado encontrado", ticket));
    }

    // Atualiza um chamado (somente o dono)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> update(@PathVariable Long id,
                                                                 @Valid @RequestBody TicketCreateDTO dto) {
        TicketResponseDTO ticket = ticketService.updateTicket(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("ok", "Chamado atualizado", ticket));
    }

    // Atualiza o status de um chamado (ADMIN)
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> updateStatus(@PathVariable Long id,
                                                                       @Valid @RequestBody TicketUpdateStatusDTO dto) {
        TicketResponseDTO ticket = ticketService.changeStatus(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("ok", "Status atualizado", ticket));
    }

    // Atribui um chamado para um usuário (ADMIN)
    @PutMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TicketResponseDTO>> assign(@PathVariable Long id,
                                                                 @PathVariable Long userId) {
        TicketResponseDTO ticket = ticketService.assignTo(id, userId);
        return ResponseEntity.ok(new ApiResponse<>("ok", "Chamado atribuído", ticket));
    }
}
