package cl.duoc.hotel.duenos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DuenoRequestDTO {
    @NotBlank(message = "Campo obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "Campo obligatorio")
    @Size(min = 7, max = 12, message = "Formato de RUT inválido")
    private String rut;

    @NotBlank(message = "Campo obligatorio")
    private String telefono;

    @NotBlank(message = "Campo obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correo;
}
