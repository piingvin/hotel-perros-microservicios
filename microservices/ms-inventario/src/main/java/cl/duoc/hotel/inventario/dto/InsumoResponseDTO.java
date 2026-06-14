package cl.duoc.hotel.inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoResponseDTO {
    private Long id;
    private String nombre;
    private Integer cantidad;
    private String unidadMedida;
    private Integer stockMinimo;
}
