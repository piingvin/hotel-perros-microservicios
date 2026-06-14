package cl.duoc.hotel.reservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * IDs de referencia a otros microservicios (Database per Service pattern).
     * No traemos las entidades completas, solo mantenemos referencias by ID.
     */
    private Long duenoId;
    private Long mascotaId;
    private Long habitacionId;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
}
