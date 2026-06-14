package cl.duoc.hotel.servicios.service;

import cl.duoc.hotel.servicios.dto.*;
import cl.duoc.hotel.servicios.exception.ResourceNotFoundException;
import cl.duoc.hotel.servicios.model.ServicioExtra;
import cl.duoc.hotel.servicios.repository.ServicioExtraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServicioExtraService {
    private final ServicioExtraRepository repository;

    public List<ServicioExtraResponseDTO> listar() {
        log.info("Listando todos los servicios extra registrados");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ServicioExtraResponseDTO obtener(Long id) {
        log.info("Obteniendo servicio extra con ID: {}", id);
        return toDto(buscar(id));
    }

    public ServicioExtraResponseDTO crear(ServicioExtraRequestDTO dto) {
        log.info("Creando nuevo servicio extra: {}", dto.getDescripcion());
        ServicioExtra saved = repository.save(ServicioExtra.builder()
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .reservaId(dto.getReservaId())
                .build());
        log.info("Servicio extra creado exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public ServicioExtraResponseDTO actualizar(Long id, ServicioExtraRequestDTO dto) {
        log.info("Actualizando servicio extra ID: {}", id);
        ServicioExtra e = buscar(id);

        e.setDescripcion(dto.getDescripcion());
        e.setPrecio(dto.getPrecio());
        e.setReservaId(dto.getReservaId());

        ServicioExtra updated = repository.save(e);
        log.info("Servicio extra ID: {} actualizado correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando servicio extra ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ServicioExtra no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Servicio extra ID: {} eliminado correctamente", id);
    }

    private ServicioExtra buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Servicio extra no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("ServicioExtra no encontrado: " + id);
                });
    }

    private ServicioExtraResponseDTO toDto(ServicioExtra e) {
        return ServicioExtraResponseDTO.builder()
                .id(e.getId())
                .descripcion(e.getDescripcion())
                .precio(e.getPrecio())
                .reservaId(e.getReservaId())
                .build();
    }
}