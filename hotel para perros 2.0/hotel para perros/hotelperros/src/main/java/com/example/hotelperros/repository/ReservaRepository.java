package com.example.hotelperros.repository;

import java.util.List;
import com.example.hotelperros.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Buscar perritos por nombre (ignorando mayúsculas y minúsculas)
    List<Reserva> findByNombrePerroContainingIgnoreCase(String nombrePerro);

    // Filtrar por el tipo de habitación
    List<Reserva> findByTipoHabitacionIgnoreCase(String tipoHabitacion);
}
