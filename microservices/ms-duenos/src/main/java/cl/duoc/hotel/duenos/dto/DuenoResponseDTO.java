package cl.duoc.hotel.duenos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DuenoResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String rut;
    private String telefono;
    private String correo;
}
