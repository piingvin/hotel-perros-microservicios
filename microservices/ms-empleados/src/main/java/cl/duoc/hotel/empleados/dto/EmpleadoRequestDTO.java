package cl.duoc.hotel.empleados.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoRequestDTO {
    @NotBlank(message = "Campo obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "Campo obligatorio")
    @Size(min = 8, max = 12, message = "Formato de RUT inválido")
    private String rut;

    @NotBlank(message = "Campo obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correo;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "El turno es obligatorio")
    private String turno;
}
