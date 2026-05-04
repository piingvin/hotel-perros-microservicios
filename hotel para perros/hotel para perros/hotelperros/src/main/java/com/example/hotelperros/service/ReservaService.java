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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final DuenoRepository duenoRepository;

    public List<ReservaResponseDTO> getReservas() {
        log.info("Obteniendo listado completo de reservas activas");
        return reservaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        log.info("Iniciando creación de reserva para el perro: {}", dto.getNombrePerro());

        if (!dto.getTipoHabitacion().equalsIgnoreCase("VIP") &&
                !dto.getTipoHabitacion().equalsIgnoreCase("ESTANDAR")) {
            log.warn("Intento de reserva con tipo de habitación no soportado por el hotel: {}", dto.getTipoHabitacion());
            throw new IllegalArgumentException("Tipo de habitación inválido. Debe ser VIP o ESTANDAR.");
        }

        Dueno dueno = duenoRepository.findById(dto.getDuenoId())
                .orElseThrow(() -> new ResourceNotFoundException("Operación denegada. Dueño no registrado con ID: " + dto.getDuenoId()));

        Reserva reserva = Reserva.builder()
                .nombrePerro(dto.getNombrePerro())
                .raza(dto.getRaza())
                .diasHospedaje(dto.getDiasHospedaje())
                .tipoHabitacion(dto.getTipoHabitacion().toUpperCase())
                .fecha(dto.getFecha()) // ¡FIX: Se agrega la fecha que faltaba!
                .dueno(dueno)
                .build();

        try {
            Reserva saved = reservaRepository.save(reserva);
            log.info("Reserva registrada exitosamente con ID: {}", saved.getId());
            return mapToDTO(saved);
        } catch (DataAccessException e) {
            log.error("Fallo de persistencia transaccional al intentar guardar la reserva", e);
            throw new RuntimeException("Error interno guardando la reserva", e);
        }
    }

    public ReservaResponseDTO getReservaId(Long id) {
        log.info("Consultando detalles de la reserva ID: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existen registros para la reserva ID: " + id));
        return mapToDTO(reserva);
    }

    public ReservaResponseDTO updateReserva(Long id, ReservaRequestDTO dto) {
        log.info("Actualizando datos de la reserva ID: {}", id);

        Reserva reservaActualizada = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actualización abortada. Reserva no encontrada con ID: " + id));

        Dueno dueno = duenoRepository.findById(dto.getDuenoId())
                .orElseThrow(() -> new ResourceNotFoundException("Actualización abortada. Dueño no registrado con ID: " + dto.getDuenoId()));

        reservaActualizada.setNombrePerro(dto.getNombrePerro());
        reservaActualizada.setRaza(dto.getRaza());
        reservaActualizada.setDiasHospedaje(dto.getDiasHospedaje());
        reservaActualizada.setTipoHabitacion(dto.getTipoHabitacion().toUpperCase());
        reservaActualizada.setFecha(dto.getFecha()); // ¡FIX: Agregado a la actualización!
        reservaActualizada.setDueno(dueno);

        Reserva saved = reservaRepository.save(reservaActualizada);
        log.info("Reserva ID: {} modificada correctamente", id);
        return mapToDTO(saved);
    }

    public void deleteReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Eliminación abortada. Reserva no encontrada con ID: " + id);
        }
        log.info("Procesando eliminación de reserva ID: {}", id);
        reservaRepository.deleteById(id);
    }

    public long totalReservas() {
        return reservaRepository.count();
    }

    private ReservaResponseDTO mapToDTO(Reserva reserva) {
        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .nombrePerro(reserva.getNombrePerro())
                .raza(reserva.getRaza())
                .diasHospedaje(reserva.getDiasHospedaje())
                .tipoHabitacion(reserva.getTipoHabitacion())
                .fecha(reserva.getFecha()) // ¡FIX: Agregado al mapeo de salida!
                .duenoNombre(reserva.getDueno() != null ? reserva.getDueno().getNombreCompleto() : null)
                .build();
    }

    public List<ReservaResponseDTO> buscarPorNombrePerro(String nombre) {
        log.info("Ejecutando búsqueda de reservas asociadas al nombre: {}", nombre);
        return reservaRepository.findByNombrePerroContainingIgnoreCase(nombre).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> filtrarPorHabitacion(String tipo) {
        log.info("Consultando ocupación para habitaciones tipo: {}", tipo);
        return reservaRepository.findByTipoHabitacionIgnoreCase(tipo).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Buscando reservas entre {} y {}", fechaInicio, fechaFin);
        return reservaRepository.findByFechaBetween(fechaInicio, fechaFin).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Double calcularCostoReserva(Long id) {
        log.info("Calculando el costo de la reserva ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        double tarifaDiaria = 0.0;
        if (reserva.getTipoHabitacion().equalsIgnoreCase("VIP")) {
            tarifaDiaria = 50.0;
        } else {
            tarifaDiaria = 25.0;
        }

        return reserva.getDiasHospedaje() * tarifaDiaria;
    }
}
