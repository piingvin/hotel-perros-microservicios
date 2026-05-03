package com.example.hotelperros.controller;

import com.example.hotelperros.dto.ReservaRequestDTO;
import com.example.hotelperros.dto.ReservaResponseDTO;
import com.example.hotelperros.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.getReservas());
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> agregarReserva(@Valid @RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO dto = reservaService.crearReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.getReservaId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(@PathVariable Long id, @Valid @RequestBody ReservaRequestDTO request) {
        return ResponseEntity.ok(reservaService.updateReserva(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalReservas() {
        return ResponseEntity.ok(reservaService.totalReservas());
    }
}