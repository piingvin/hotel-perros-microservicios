package cl.duoc.hotel.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteRequestDTO {
    @NotBlank(message = "Campo obligatorio")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipo;

    @NotBlank(message = "El contenido del reporte no puede estar vacío")
    private String contenido;

    // fechaGeneracion se asigna automáticamente en el servicio
    private LocalDateTime fechaGeneracion;

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long empleadoId;
}
