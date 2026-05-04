package com.example.hotelperros.repository;

import com.example.hotelperros.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Genera automáticamente la query SQL: SELECT * FROM reservas WHERE nombre_perro LIKE '%nombre%'
     * Ignora el Case Sensitivity (mayúsculas/minúsculas) para facilitar la búsqueda al usuario.
     */
    List<Reserva> findByNombrePerroContainingIgnoreCase(String nombrePerro);

    /**
     * Genera automáticamente la query SQL: SELECT * FROM reservas WHERE tipo_habitacion = 'tipo'
     * Filtra los registros asegurando que "VIP" y "vip" sean tratados como la misma categoría.
     */
    List<Reserva> findByTipoHabitacionIgnoreCase(String tipoHabitacion);

    List<Reserva> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
