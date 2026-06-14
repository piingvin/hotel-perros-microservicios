package cl.duoc.hotel.servicios.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioExtraResponseDTO {
    private Long id;
    private String descripcion;
    private Double precio;
    private Long reservaId;
}