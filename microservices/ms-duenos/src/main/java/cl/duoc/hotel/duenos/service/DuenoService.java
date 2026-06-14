package cl.duoc.hotel.duenos.service;

import cl.duoc.hotel.duenos.dto.*;
import cl.duoc.hotel.duenos.exception.ResourceNotFoundException;
import cl.duoc.hotel.duenos.model.Dueno;
import cl.duoc.hotel.duenos.repository.DuenoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DuenoService {
    private final DuenoRepository repository;

    public List<DuenoResponseDTO> listar() {
        log.info("Listando todos los dueños registrados");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DuenoResponseDTO obtener(Long id) {
        log.info("Obteniendo dueño con ID: {}", id);
        return toDto(buscar(id));
    }

    public DuenoResponseDTO crear(DuenoRequestDTO dto) {
        log.info("Creando nuevo dueño: {}", dto.getNombreCompleto());
        Dueno saved = repository.save(Dueno.builder()
                .nombreCompleto(dto.getNombreCompleto())
                .rut(dto.getRut())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .build());
        log.info("Dueño creado exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public DuenoResponseDTO actualizar(Long id, DuenoRequestDTO dto) {
        log.info("Actualizando dueño ID: {}", id);
        Dueno e = buscar(id);
        e.setNombreCompleto(dto.getNombreCompleto());
        e.setRut(dto.getRut());
        e.setTelefono(dto.getTelefono());
        e.setCorreo(dto.getCorreo());
        Dueno updated = repository.save(e);
        log.info("Dueño ID: {} actualizado correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando dueño ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Dueno no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Dueño ID: {} eliminado correctamente", id);
    }

    private Dueno buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Dueño no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Dueno no encontrado: " + id);
                });
    }

    private DuenoResponseDTO toDto(Dueno e) {
        return DuenoResponseDTO.builder()
                .id(e.getId())
                .nombreCompleto(e.getNombreCompleto())
                .rut(e.getRut())
                .telefono(e.getTelefono())
                .correo(e.getCorreo())
                .build();
    }
}
