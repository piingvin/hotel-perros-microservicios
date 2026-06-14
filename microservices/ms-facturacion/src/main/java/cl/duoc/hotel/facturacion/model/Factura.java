package cl.duoc.hotel.facturacion.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String concepto;

    /**
     * Fecha de emisión de la factura.
     */
    private LocalDate fechaEmision;

    private String estado;
    private String metodoPago;

    /**
     * ID de la reserva asociada (referencia al MS de Reservas).
     * Permite vincular facturas con reservas en el microservicio remoto.
     */
    private Long reservaId;

    /**
     * Monto de la factura (calculado desde el costo de la reserva remota).
     */
    private Double monto;
}
