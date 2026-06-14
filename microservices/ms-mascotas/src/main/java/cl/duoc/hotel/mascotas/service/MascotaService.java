package cl.duoc.hotel.mascotas.service;

import cl.duoc.hotel.mascotas.dto.*;
import cl.duoc.hotel.mascotas.exception.ResourceNotFoundException;
import cl.duoc.hotel.mascotas.model.Mascota;
import cl.duoc.hotel.mascotas.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MascotaService {
    private final MascotaRepository repository;

    public List<MascotaResponseDTO> listar() {
        log.info("Listando todas las mascotas registradas");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public MascotaResponseDTO obtener(Long id) {
        log.info("Obteniendo mascota con ID: {}", id);
        return toDto(buscar(id));
    }

    public MascotaResponseDTO crear(MascotaRequestDTO dto) {
        log.info("Creando nueva mascota: {}", dto.getNombre());
        Mascota saved = repository.save(Mascota.builder()
                .nombre(dto.getNombre())
                .raza(dto.getRaza())
                .edad(dto.getEdad())
                .peso(dto.getPeso())
                .vacunasAlDia(dto.getVacunasAlDia())
                .duenoId(dto.getDuenoId())
                .build());
        log.info("Mascota creada exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public MascotaResponseDTO actualizar(Long id, MascotaRequestDTO dto) {
        log.info("Actualizando mascota ID: {}", id);
        Mascota e = buscar(id);
        Mascota updated = repository.save(Mascota.builder()
                .id(e.getId())
                .nombre(dto.getNombre())
                .raza(dto.getRaza())
                .edad(dto.getEdad())
                .peso(dto.getPeso())
                .vacunasAlDia(dto.getVacunasAlDia())
                .duenoId(dto.getDuenoId())
                .build());
        log.info("Mascota ID: {} actualizada correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando mascota ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Mascota no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Mascota ID: {} eliminada correctamente", id);
    }

    private Mascota buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Mascota no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Mascota no encontrado: " + id);
                });
    }

    private MascotaResponseDTO toDto(Mascota e) {
        return MascotaResponseDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .raza(e.getRaza())
                .edad(e.getEdad())
                .peso(e.getPeso())
                .vacunasAlDia(e.getVacunasAlDia())
                .duenoId(e.getDuenoId())
                .build();
    }
}
