package cl.duoc.hotel.facturacion.service;

import cl.duoc.hotel.facturacion.client.ReservaClient;
import cl.duoc.hotel.facturacion.dto.FacturaRequestDTO;
import cl.duoc.hotel.facturacion.dto.FacturaResponseDTO;
import cl.duoc.hotel.facturacion.exception.ResourceNotFoundException;
import cl.duoc.hotel.facturacion.model.Factura;
import cl.duoc.hotel.facturacion.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository repository;
    private final ReservaClient reservaClient;

    public List<FacturaResponseDTO> listar() {
        log.info("Listando todas las facturas registradas en el sistema");
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public FacturaResponseDTO obtener(Long id) {
        log.info("Obteniendo detalles de la factura con ID: {}", id);
        return toDto(buscar(id));
    }

    public FacturaResponseDTO crear(FacturaRequestDTO dto) {
        log.info("Iniciando la generación de factura para la reserva ID: {}", dto.getReservaId());

        // 1. Validación de comunicación sincrónica vía OpenFeign
        try {
            Object reserva = reservaClient.obtenerReserva(dto.getReservaId());
            if (reserva == null) {
                throw new ResourceNotFoundException("La reserva no existe en el microservicio remoto.");
            }
        } catch (Exception e) {
            log.error("Fallo de comunicación con ms-reservas al intentar validar el ID: {}", dto.getReservaId(), e);
            throw new RuntimeException("No se pudo validar la reserva. Verifique que el microservicio remoto esté activo.");
        }

        // 2. Persistencia en la tabla de base de datos SQL
        Factura nuevaFactura = Factura.builder()
                .concepto(dto.getConcepto())
                .reservaId(dto.getReservaId())
                .monto(dto.getMonto())
                .fechaEmision(LocalDate.now())
                .estado("EMITIDA")
                .metodoPago(dto.getMetodoPago())
                .build();

        Factura guardada = repository.save(nuevaFactura);
        log.info("Factura insertada correctamente en la base de datos con ID: {}", guardada.getId());

        return toDto(guardada);
    }

    public FacturaResponseDTO actualizar(Long id, FacturaRequestDTO dto) {
        log.info("Modificando datos de la factura ID: {}", id);
        Factura e = buscar(id);

        // Si se intenta cambiar la reserva asociada, se vuelve a validar por red
        if (!e.getReservaId().equals(dto.getReservaId())) {
            try {
                reservaClient.obtenerReserva(dto.getReservaId());
            } catch (Exception ex) {
                log.error("Error al re-validar la nueva reserva ID: {}", dto.getReservaId());
                throw new RuntimeException("Actualización abortada. La nueva reserva no es válida o el servicio no responde.");
            }
        }

        e.setConcepto(dto.getConcepto());
        e.setReservaId(dto.getReservaId());
        e.setMonto(dto.getMonto());
        e.setMetodoPago(dto.getMetodoPago());

        Factura updated = repository.save(e);
        log.info("Factura ID: {} modificada exitosamente", id);
        return toDto(updated);
    }

    public void eliminar(Long id) {
        log.info("Procesando la eliminación de la factura ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Factura no encontrada para eliminación: " + id);
        }
        repository.deleteById(id);
        log.info("Factura ID: {} removida del sistema correctamente", id);
    }

    private Factura buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Búsqueda fallida. No existe la factura con ID: {}", id);
                    return new ResourceNotFoundException("Factura no encontrada: " + id);
                });
    }

    private FacturaResponseDTO toDto(Factura e) {
        return FacturaResponseDTO.builder()
                .id(e.getId())
                .concepto(e.getConcepto())
                .reservaId(e.getReservaId())
                .monto(e.getMonto())
                .fechaEmision(e.getFechaEmision())
                .estado(e.getEstado())
                .metodoPago(e.getMetodoPago())
                .build();
    }
}