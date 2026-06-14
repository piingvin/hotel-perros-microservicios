package cl.duoc.hotel.notificaciones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionRequestDTO {
    @NotBlank(message = "Campo obligatorio")
    @Size(min = 5, max = 500, message = "El mensaje debe tener entre 5 y 500 caracteres")
    private String mensaje;

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "Campo obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String destinatarioCorreo;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    // fechaEnvio se asigna automáticamente en el servicio
    private LocalDateTime fechaEnvio;

    @NotNull(message = "El ID del dueño es obligatorio")
    private Long duenoId;
}
