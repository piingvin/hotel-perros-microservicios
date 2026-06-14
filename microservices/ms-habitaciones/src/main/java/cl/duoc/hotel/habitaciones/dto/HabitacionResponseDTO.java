package cl.duoc.hotel.habitaciones.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionResponseDTO {
    private Long id;
    private String tipo;
    private String numero;
    private Double precioNoche;
    private String estado;
}
