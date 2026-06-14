package cl.duoc.hotel.empleados.service;

import cl.duoc.hotel.empleados.dto.*;
import cl.duoc.hotel.empleados.exception.ResourceNotFoundException;
import cl.duoc.hotel.empleados.model.Empleado;
import cl.duoc.hotel.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository repository;

    public List<EmpleadoResponseDTO> listar() {
        log.info("Listando todos los empleados registrados");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public EmpleadoResponseDTO obtener(Long id) {
        log.info("Obteniendo empleado con ID: {}", id);
        return toDto(buscar(id));
    }

    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) {
        log.info("Creando nuevo empleado: {}", dto.getNombre());
        Empleado saved = repository.save(Empleado.builder()
                .nombre(dto.getNombre())
                .rut(dto.getRut())
                .correo(dto.getCorreo())
                .cargo(dto.getCargo())
                .turno(dto.getTurno())
                .build());
        log.info("Empleado creado exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto) {
        log.info("Actualizando empleado ID: {}", id);
        Empleado e = buscar(id);
        Empleado updated = repository.save(Empleado.builder()
                .id(e.getId())
                .nombre(dto.getNombre())
                .rut(dto.getRut())
                .correo(dto.getCorreo())
                .cargo(dto.getCargo())
                .turno(dto.getTurno())
                .build());
        log.info("Empleado ID: {} actualizado correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando empleado ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Empleado no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Empleado ID: {} eliminado correctamente", id);
    }

    private Empleado buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Empleado no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Empleado no encontrado: " + id);
                });
    }

    private EmpleadoResponseDTO toDto(Empleado e) {
        return EmpleadoResponseDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .rut(e.getRut())
                .correo(e.getCorreo())
                .cargo(e.getCargo())
                .turno(e.getTurno())
                .build();
    }
}
