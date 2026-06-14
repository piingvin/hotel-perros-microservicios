package cl.duoc.hotel.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private String asunto;
    private String destinatarioCorreo;
    private String estado;
    private LocalDateTime fechaEnvio;
    private Long duenoId;
}
