package cl.duoc.hotel.mascotas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String raza;
    private Integer edad;
    private Double peso;
    private Boolean vacunasAlDia;
    private Long duenoId;
}
