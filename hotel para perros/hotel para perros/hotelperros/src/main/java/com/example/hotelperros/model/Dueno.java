package com.example.hotelperros.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "duenos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    /**
     * Relación Bidireccional (1:N): Un dueño puede tener asociadas múltiples reservas de forma simultánea.
     * - cascade = ALL: Permite que si se elimina un Dueño, se eliminen automáticamente todas sus reservas.
     * - orphanRemoval = true: Si una reserva se quita de esta lista, se elimina de la base de datos,
     *   evitando registros "huérfanos" (reservas sin dueño asignado).
     */
    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
}