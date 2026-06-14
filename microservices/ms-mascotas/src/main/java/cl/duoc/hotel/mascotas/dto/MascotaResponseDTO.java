package cl.duoc.hotel.mascotas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaResponseDTO {
    private Long id;
    private String nombre;
    private String raza;
    private Integer edad;
    private Double peso;
    private Boolean vacunasAlDia;
    private Long duenoId;
}
