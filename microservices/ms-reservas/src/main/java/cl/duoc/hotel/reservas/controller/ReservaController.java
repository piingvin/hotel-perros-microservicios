package cl.duoc.hotel.reservas.controller;

import cl.duoc.hotel.reservas.dto.ReservaRequestDTO;
import cl.duoc.hotel.reservas.dto.ReservaResponseDTO;
import cl.duoc.hotel.reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // Endpoint 1: GET /api/v1/reservas
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.getReservas());
    }

    // Endpoint 2: POST /api/v1/reservas
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> agregarReserva(@Valid @RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO dto = reservaService.crearReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Endpoint 3: GET /api/v1/reservas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.getReservaId(id));
    }

    // Endpoint 4: PUT /api/v1/reservas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(@PathVariable Long id, @Valid @RequestBody ReservaRequestDTO request) {
        return ResponseEntity.ok(reservaService.updateReserva(id, request));
    }

    // Endpoint 5: DELETE /api/v1/reservas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint 6: GET /api/v1/reservas/total
    @GetMapping("/total")
    public ResponseEntity<Long> totalReservas() {
        return ResponseEntity.ok(reservaService.totalReservas());
    }

    // Endpoint 7: GET /api/v1/reservas/rango
    // Filtra las reservas por un rango de fechas (fecha de inicio y fin)
    @GetMapping("/rango")
    public ResponseEntity<List<ReservaResponseDTO>> filtrarPorRangoFechas(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<ReservaResponseDTO> reservas = reservaService.buscarPorRangoFechas(fechaInicio, fechaFin);
        if(reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }

    // Endpoint 8: GET /api/v1/reservas/{id}/costo
    // Calcula el costo total de la reserva (Próximamente vía WebClient)
    @GetMapping("/{id}/costo")
    public ResponseEntity<Double> calcularCostoReserva(@PathVariable Long id) {
        Double costoTotal = reservaService.calcularCostoReserva(id);
        return ResponseEntity.ok(costoTotal);
    }
}