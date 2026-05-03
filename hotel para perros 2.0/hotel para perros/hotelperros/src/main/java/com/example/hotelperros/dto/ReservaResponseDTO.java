package com.example.hotelperros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDTO {

    private Long id;
    private String nombrePerro;
    private String raza;
    private Integer diasHospedaje;
    private String tipoHabitacion;
    private String duenoNombre;
}