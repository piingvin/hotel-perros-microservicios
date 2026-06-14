package cl.duoc.hotel.reservas.repository;

import cl.duoc.hotel.reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Busca reservas dentro de un rango de fechas (Database per Service Pattern).
     * Utiliza fechaInicio para búsqueda de reservas activas en ese período.
     */
    List<Reserva> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
