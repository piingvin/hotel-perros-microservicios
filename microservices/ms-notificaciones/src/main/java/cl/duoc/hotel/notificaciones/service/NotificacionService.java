package cl.duoc.hotel.notificaciones.service;

import cl.duoc.hotel.notificaciones.dto.*;
import cl.duoc.hotel.notificaciones.exception.ResourceNotFoundException;
import cl.duoc.hotel.notificaciones.model.Notificacion;
import cl.duoc.hotel.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificacionService {
    private final NotificacionRepository repository;

    public List<NotificacionResponseDTO> listar() {
        log.info("Listando todas las notificaciones registradas");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public NotificacionResponseDTO obtener(Long id) {
        log.info("Obteniendo notificación con ID: {}", id);
        return toDto(buscar(id));
    }

    public NotificacionResponseDTO crear(NotificacionRequestDTO dto) {
        log.info("Creando nueva notificación: {}", dto.getMensaje());
        // Asignar fecha de envío por defecto
        LocalDateTime fecha = LocalDateTime.now();

        Notificacion saved = repository.save(Notificacion.builder()
                .mensaje(dto.getMensaje())
                .asunto(dto.getAsunto())
                .destinatarioCorreo(dto.getDestinatarioCorreo())
                .estado(dto.getEstado())
                .fechaEnvio(fecha)
                .duenoId(dto.getDuenoId())
                .build());
        log.info("Notificación creada exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public NotificacionResponseDTO actualizar(Long id, NotificacionRequestDTO dto) {
        log.info("Actualizando notificación ID: {}", id);
        Notificacion e = buscar(id);
        Notificacion updated = repository.save(Notificacion.builder()
                .id(e.getId())
                .mensaje(dto.getMensaje())
                .asunto(dto.getAsunto())
                .destinatarioCorreo(dto.getDestinatarioCorreo())
                .estado(dto.getEstado())
                .fechaEnvio(dto.getFechaEnvio() != null ? dto.getFechaEnvio() : e.getFechaEnvio())
                .duenoId(dto.getDuenoId())
                .build());
        log.info("Notificación ID: {} actualizada correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando notificación ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Notificacion no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Notificación ID: {} eliminada correctamente", id);
    }

    private Notificacion buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Notificación no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Notificacion no encontrado: " + id);
                });
    }

    private NotificacionResponseDTO toDto(Notificacion e) {
        return NotificacionResponseDTO.builder()
                .id(e.getId())
                .mensaje(e.getMensaje())
                .asunto(e.getAsunto())
                .destinatarioCorreo(e.getDestinatarioCorreo())
                .estado(e.getEstado())
                .fechaEnvio(e.getFechaEnvio())
                .duenoId(e.getDuenoId())
                .build();
    }
}
