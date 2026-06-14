package cl.duoc.hotel.reportes.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteResponseDTO {
    private Long id;
    private String titulo;
    private String tipo;
    private String contenido;
    private LocalDateTime fechaGeneracion;
    private Long empleadoId;
}
