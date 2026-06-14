package cl.duoc.hotel.empleados.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoResponseDTO {
    private Long id;
    private String nombre;
    private String rut;
    private String correo;
    private String cargo;
    private String turno;
}
