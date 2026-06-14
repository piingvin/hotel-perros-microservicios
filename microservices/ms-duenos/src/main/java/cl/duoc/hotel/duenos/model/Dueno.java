package cl.duoc.hotel.duenos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "duenos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dueno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    private String rut;
    private String telefono;
    private String correo;
}
