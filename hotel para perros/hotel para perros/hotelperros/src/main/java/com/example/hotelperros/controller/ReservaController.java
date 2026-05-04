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

    // Endpoint 1: GET /api/v1/reservas
    // Lista todas las reservas activas en el sistema
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.getReservas());
    }

    // Endpoint 2: POST /api/v1/reservas
    // Registra una nueva reserva validando los datos de entrada
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> agregarReserva(@Valid @RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO dto = reservaService.crearReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Endpoint 3: GET /api/v1/reservas/{id}
    // Busca los detalles de una reserva específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.getReservaId(id));
    }

    // Endpoint 4: PUT /api/v1/reservas/{id}
    // Actualiza la información completa de una reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(@PathVariable Long id, @Valid @RequestBody ReservaRequestDTO request) {
        return ResponseEntity.ok(reservaService.updateReserva(id, request));
    }

    // Endpoint 5: DELETE /api/v1/reservas/{id}
    // Elimina una reserva del sistema mediante su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint 6: GET /api/v1/reservas/total
    // Retorna la cantidad total de reservas registradas en el hotel
    @GetMapping("/total")
    public ResponseEntity<Long> totalReservas() {
        return ResponseEntity.ok(reservaService.totalReservas());
    }

    // Endpoint 7: GET /api/v1/reservas/buscar?nombre={nombre}
    // Busca reservas según el nombre de la mascota, retornando 204 si no hay coincidencias
    @GetMapping("/buscar")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorNombrePerro(@RequestParam String nombre) {
        List<ReservaResponseDTO> reservas = reservaService.buscarPorNombrePerro(nombre);
        if(reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }

    // Endpoint 8: GET /api/v1/reservas/habitacion/{tipo}
    // Filtra las estadías activas por tipo de habitación (VIP o ESTANDAR)
    @GetMapping("/habitacion/{tipo}")
    public ResponseEntity<List<ReservaResponseDTO>> filtrarPorHabitacion(@PathVariable String tipo) {
        List<ReservaResponseDTO> reservas = reservaService.filtrarPorHabitacion(tipo);
        if(reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }
    // Endpoint 9: GET /api/v1/reservas/rango
    // Filtra las reservas por un rango de fechas (fecha de inicio y fin)
    @GetMapping("/rango")
    public ResponseEntity<List<ReservaResponseDTO>> filtrarPorRangoFechas(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate fechaInicio,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate fechaFin) {

        List<ReservaResponseDTO> reservas = reservaService.buscarPorRangoFechas(fechaInicio, fechaFin);
        if(reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }
    // Endpoint 10: GET /api/v1/reservas/{id}/costo
    // Calcula el costo total de la reserva según la habitación y días de hospedaje
    @GetMapping("/{id}/costo")
    public ResponseEntity<Double> calcularCostoReserva(@PathVariable Long id) {
        Double costoTotal = reservaService.calcularCostoReserva(id);
        return ResponseEntity.ok(costoTotal);
    }

}