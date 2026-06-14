package cl.duoc.hotel.notificaciones.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponseDTO {
    private Long id;
    private String mensaje;
    private String asunto;
    private String destinatarioCorreo;
    private String estado;
    private LocalDateTime fechaEnvio;
    private Long duenoId;
}
