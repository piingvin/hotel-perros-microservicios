package com.example.hotelperros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaRequestDTO {

    @NotBlank(message = "El nombre del perro es obligatorio")
    private String nombrePerro;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotNull(message = "Los dias de hospedaje son obligatorios")
    @Positive(message = "Los dias de hospedaje deben ser mayores que 0")
    private Integer diasHospedaje;

    @NotBlank(message = "El tipo de habitacion es obligatorio")
    private String tipoHabitacion;

    @NotNull(message = "La fecha de la reserva es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El ID del dueno es obligatorio")
    private Long duenoId;
}