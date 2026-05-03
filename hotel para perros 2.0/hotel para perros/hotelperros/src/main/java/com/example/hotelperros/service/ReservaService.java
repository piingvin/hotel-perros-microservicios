package com.example.hotelperros.service;

import com.example.hotelperros.dto.ReservaRequestDTO;
import com.example.hotelperros.dto.ReservaResponseDTO;
import com.example.hotelperros.exception.ResourceNotFoundException;
import com.example.hotelperros.model.Dueno;
import com.example.hotelperros.model.Reserva;
import com.example.hotelperros.repository.DuenoRepository;
import com.example.hotelperros.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final DuenoRepository duenoRepository; // Movido aquí desde el controlador

    public List<ReservaResponseDTO> getReservas() {
        log.info("Obteniendo listado completo de reservas");
        return reservaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        log.info("Iniciando creación de reserva para el perro: {}", dto.getNombrePerro());

        // REGLA DE NEGOCIO: Validar tipo de habitación
        if (!dto.getTipoHabitacion().equalsIgnoreCase("VIP") &&
                !dto.getTipoHabitacion().equalsIgnoreCase("ESTANDAR")) {
            log.warn("Intento de reserva con tipo de habitación inválido: {}", dto.getTipoHabitacion());
            throw new IllegalArgumentException("Tipo de habitación inválido. Debe ser VIP o ESTANDAR.");
        }

        Dueno dueno = duenoRepository.findById(dto.getDuenoId())
                .orElseThrow(() -> new ResourceNotFoundException("Dueño no encontrado con ID: " + dto.getDuenoId()));

        Reserva reserva = Reserva.builder()
                .nombrePerro(dto.getNombrePerro())
                .raza(dto.getRaza())
                .diasHospedaje(dto.getDiasHospedaje())
                .tipoHabitacion(dto.getTipoHabitacion().toUpperCase())
                .dueno(dueno)
                .build();

        try {
            Reserva saved = reservaRepository.save(reserva);
            log.info("Reserva guardada exitosamente con id: {}", saved.getId());
            return mapToDTO(saved);
        } catch (DataAccessException e) {
            log.error("Error en la base de datos al guardar la reserva", e);
            throw new RuntimeException("Error guardando reserva", e);
        }
    }

    public ReservaResponseDTO getReservaId(Long id) {
        log.info("Buscando reserva con id: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        return mapToDTO(reserva);
    }

    public ReservaResponseDTO updateReserva(Long id, ReservaRequestDTO dto) {
        log.info("Iniciando actualización de reserva con id: {}", id);

        Reserva reservaActualizada = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

        Dueno dueno = duenoRepository.findById(dto.getDuenoId())
                .orElseThrow(() -> new ResourceNotFoundException("Dueño no encontrado con ID: " + dto.getDuenoId()));

        reservaActualizada.setNombrePerro(dto.getNombrePerro());
        reservaActualizada.setRaza(dto.getRaza());
        reservaActualizada.setDiasHospedaje(dto.getDiasHospedaje());
        reservaActualizada.setTipoHabitacion(dto.getTipoHabitacion().toUpperCase());
        reservaActualizada.setDueno(dueno);

        Reserva saved = reservaRepository.save(reservaActualizada);
        log.info("Reserva con id: {} actualizada correctamente", id);
        return mapToDTO(saved);
    }

    public void deleteReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con id: " + id);
        }
        log.info("Eliminando reserva con id: {}", id);
        reservaRepository.deleteById(id);
    }

    public long totalReservas() {
        return reservaRepository.count();
    }

    // El mapeo ahora es responsabilidad del servicio
    private ReservaResponseDTO mapToDTO(Reserva reserva) {
        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .nombrePerro(reserva.getNombrePerro())
                .raza(reserva.getRaza())
                .diasHospedaje(reserva.getDiasHospedaje())
                .tipoHabitacion(reserva.getTipoHabitacion())
                .duenoNombre(reserva.getDueno() != null ? reserva.getDueno().getNombreCompleto() : null)
                .build();
    }
}
