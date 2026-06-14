package cl.duoc.hotel.reservas.service;

import cl.duoc.hotel.reservas.client.HabitacionClient;
import cl.duoc.hotel.reservas.dto.HabitacionClientDTO;
import cl.duoc.hotel.reservas.dto.ReservaRequestDTO;
import cl.duoc.hotel.reservas.dto.ReservaResponseDTO;
import cl.duoc.hotel.reservas.exception.ResourceNotFoundException;
import cl.duoc.hotel.reservas.model.Reserva;
import cl.duoc.hotel.reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HabitacionClient habitacionClient; // Inyección de FeignClient

    public List<ReservaResponseDTO> getReservas() {
        log.info("Obteniendo listado completo de reservas activas");
        return reservaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        log.info("Iniciando creación de reserva para mascota ID: {} en habitación ID: {}", dto.getMascotaId(), dto.getHabitacionId());

        Reserva reserva = Reserva.builder()
                .duenoId(dto.getDuenoId())
                .mascotaId(dto.getMascotaId())
                .habitacionId(dto.getHabitacionId())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .estado("PENDIENTE")
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

        Reserva updated = Reserva.builder()
                .id(reservaActualizada.getId())
                .duenoId(dto.getDuenoId())
                .mascotaId(dto.getMascotaId())
                .habitacionId(dto.getHabitacionId())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .estado(reservaActualizada.getEstado())
                .build();

        Reserva saved = reservaRepository.save(updated);
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
                .duenoId(reserva.getDuenoId())
                .mascotaId(reserva.getMascotaId())
                .habitacionId(reserva.getHabitacionId())
                .fechaInicio(reserva.getFechaInicio())
                .fechaFin(reserva.getFechaFin())
                .estado(reserva.getEstado())
                .build();
    }

    public List<ReservaResponseDTO> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Buscando reservas entre {} y {}", fechaInicio, fechaFin);
        return reservaRepository.findByFechaInicioBetween(fechaInicio, fechaFin).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Double calcularCostoReserva(Long id) {
        log.info("Calculando el costo de la reserva ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        // Calcular la diferencia de días reales de hospedaje
        long diasEstadia = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
        if (diasEstadia == 0) {
            diasEstadia = 1; // Mínimo cobrar una noche
        }

        try {
            // Consumo de microservicio sincrónico con Feign
            HabitacionClientDTO habitacion = habitacionClient.obtenerHabitacion(reserva.getHabitacionId());
            if (habitacion == null || habitacion.getPrecioNoche() == null) {
                throw new RuntimeException("El servicio remoto no retornó un precio válido.");
            }
            return diasEstadia * habitacion.getPrecioNoche();
        } catch (Exception e) {
            log.error("Fallo de comunicación con ms-habitaciones para la habitación ID: {}", reserva.getHabitacionId(), e);
            throw new RuntimeException("No se pudo calcular el costo debido a un error de red interno.");
        }
    }
}