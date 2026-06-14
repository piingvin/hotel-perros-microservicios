package cl.duoc.hotel.inventario.service;

import cl.duoc.hotel.inventario.dto.*;
import cl.duoc.hotel.inventario.exception.ResourceNotFoundException;
import cl.duoc.hotel.inventario.model.Insumo;
import cl.duoc.hotel.inventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InsumoService {
    private final InsumoRepository repository;

    public List<InsumoResponseDTO> listar() {
        log.info("Listando todos los insumos registrados");
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public InsumoResponseDTO obtener(Long id) {
        log.info("Obteniendo insumo con ID: {}", id);
        return toDto(buscar(id));
    }

    public InsumoResponseDTO crear(InsumoRequestDTO dto) {
        log.info("Creando nuevo insumo: {}", dto.getNombre());
        Insumo saved = repository.save(Insumo.builder()
                .nombre(dto.getNombre())
                .cantidad(dto.getCantidad())
                .unidadMedida(dto.getUnidadMedida())
                .stockMinimo(dto.getStockMinimo())
                .build());
        log.info("Insumo creado exitosamente con ID: {}", saved.getId());
        return toDto(saved);
    }

    public InsumoResponseDTO actualizar(Long id, InsumoRequestDTO dto) {
        log.info("Actualizando insumo ID: {}", id);
        Insumo e = buscar(id);
        Insumo updated = repository.save(Insumo.builder()
                .id(e.getId())
                .nombre(dto.getNombre())
                .cantidad(dto.getCantidad())
                .unidadMedida(dto.getUnidadMedida())
                .stockMinimo(dto.getStockMinimo())
                .build());
        log.info("Insumo ID: {} actualizado correctamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando insumo ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Insumo no encontrado: " + id);
        }
        repository.deleteById(id);
        log.info("Insumo ID: {} eliminado correctamente", id);
    }

    private Insumo buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Insumo no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Insumo no encontrado: " + id);
                });
    }

    private InsumoResponseDTO toDto(Insumo e) {
        return InsumoResponseDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .cantidad(e.getCantidad())
                .unidadMedida(e.getUnidadMedida())
                .stockMinimo(e.getStockMinimo())
                .build();
    }
}
