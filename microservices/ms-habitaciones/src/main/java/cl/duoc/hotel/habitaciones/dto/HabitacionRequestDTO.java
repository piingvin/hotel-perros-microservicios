package cl.duoc.hotel.habitaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionRequestDTO {
    @NotBlank(message = "Campo obligatorio")
    @Size(min = 3, max = 50, message = "El tipo debe tener entre 3 y 50 caracteres")
    private String tipo;

    @NotBlank(message = "El número de habitación es obligatorio")
    private String numero;

    @NotNull(message = "El precio por noche es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precioNoche;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
