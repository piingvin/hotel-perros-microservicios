package cl.duoc.hotel.facturacion.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaResponseDTO {
    private Long id;
    private String concepto;
    private Long reservaId;
    private Double monto;
    private LocalDate fechaEmision;
    private String estado;
    private String metodoPago;
}