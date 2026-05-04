package com.example.hotelperros.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombrePerro;

    private String raza;

    private Integer diasHospedaje;

    private String tipoHabitacion;

    private LocalDate fecha;

    /**
     * Relación Bidireccional (N:1): Múltiples reservas pueden pertenecer a un único dueño.
     * - FetchType.LAZY: Optimización de rendimiento. El objeto 'Dueno' solo se consultará
     *   en la base de datos cuando se invoque explícitamente (ej: al hacer reserva.getDueno()).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dueno_id")
    private Dueno dueno;
}