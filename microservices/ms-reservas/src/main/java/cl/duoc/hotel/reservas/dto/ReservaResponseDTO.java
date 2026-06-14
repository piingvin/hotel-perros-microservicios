package cl.duoc.hotel.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDTO {

    private Long id;
    private Long duenoId;
    private Long mascotaId;
    private Long habitacionId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
}