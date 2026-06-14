package cl.duoc.hotel.reportes.service;

import cl.duoc.hotel.reportes.dto.*;
import cl.duoc.hotel.reportes.exception.ResourceNotFoundException;
import cl.duoc.hotel.reportes.model.Reporte;
import cl.duoc.hotel.reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReporteService {
    private final ReporteRepository repository;

    public List<ReporteResponseDTO> listar() {
        log.info("Listando todos los reportes registrados");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ReporteResponseDTO obtener(Long id) {
        log.info("Obteniendo reporte con ID: {}", id);
        return toDto(buscar(id));
    }

    public ReporteResponseDTO crear(ReporteRequestDTO dto) {
        log.info("Creando nuevo reporte: {}", dto.getTitulo());
        // Asignar fecha de generación por defecto
        LocalDateTime fecha = LocalDateTime.now();

        Reporte saved = repository.save(Reporte.builder()
                .titulo(dto.getTitulo())
                .tipo(dto.getTipo())
                .contenido(dto.getContenido())
                .fechaGeneracion(fecha)
                .empleadoId(dto.getEmpleadoId())
                .build());
        log.info("Reporte creado exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public ReporteResponseDTO actualizar(Long id, ReporteRequestDTO dto) {
        log.info("Actualizando reporte ID: {}", id);
        Reporte e = buscar(id);
        Reporte updated = repository.save(Reporte.builder()
                .id(e.getId())
                .titulo(dto.getTitulo())
                .tipo(dto.getTipo())
                .contenido(dto.getContenido())
                .fechaGeneracion(dto.getFechaGeneracion() != null ? dto.getFechaGeneracion() : e.getFechaGeneracion())
                .empleadoId(dto.getEmpleadoId())
                .build());
        log.info("Reporte ID: {} actualizado correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando reporte ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Reporte no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Reporte ID: {} eliminado correctamente", id);
    }

    private Reporte buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reporte no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Reporte no encontrado: " + id);
                });
    }

    private ReporteResponseDTO toDto(Reporte e) {
        return ReporteResponseDTO.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .tipo(e.getTipo())
                .contenido(e.getContenido())
                .fechaGeneracion(e.getFechaGeneracion())
                .empleadoId(e.getEmpleadoId())
                .build();
    }
}
