package cl.duoc.hotel.habitaciones.service;

import cl.duoc.hotel.habitaciones.dto.*;
import cl.duoc.hotel.habitaciones.exception.ResourceNotFoundException;
import cl.duoc.hotel.habitaciones.model.Habitacion;
import cl.duoc.hotel.habitaciones.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HabitacionService {
    private final HabitacionRepository repository;

    public List<HabitacionResponseDTO> listar() {
        log.info("Listando todas las habitaciones registradas");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public HabitacionResponseDTO obtener(Long id) {
        log.info("Obteniendo habitación con ID: {}", id);
        return toDto(buscar(id));
    }

    public HabitacionResponseDTO crear(HabitacionRequestDTO dto) {
        log.info("Creando nueva habitación tipo: {}", dto.getTipo());
        Habitacion saved = repository.save(Habitacion.builder()
                .tipo(dto.getTipo())
                .numero(dto.getNumero())
                .precioNoche(dto.getPrecioNoche())
                .estado(dto.getEstado())
                .build());
        log.info("Habitación creada exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public HabitacionResponseDTO actualizar(Long id, HabitacionRequestDTO dto) {
        log.info("Actualizando habitación ID: {}", id);
        Habitacion e = buscar(id);
        Habitacion updated = repository.save(Habitacion.builder()
                .id(e.getId())
                .tipo(dto.getTipo())
                .numero(dto.getNumero())
                .precioNoche(dto.getPrecioNoche())
                .estado(dto.getEstado())
                .build());
        log.info("Habitación ID: {} actualizada correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando habitación ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Habitacion no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Habitación ID: {} eliminada correctamente", id);
    }

    private Habitacion buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Habitación no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Habitacion no encontrado: " + id);
                });
    }

    private HabitacionResponseDTO toDto(Habitacion e) {
        return HabitacionResponseDTO.builder()
                .id(e.getId())
                .tipo(e.getTipo())
                .numero(e.getNumero())
                .precioNoche(e.getPrecioNoche())
                .estado(e.getEstado())
                .build();
    }
}
