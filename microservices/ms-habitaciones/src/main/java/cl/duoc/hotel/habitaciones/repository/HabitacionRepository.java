package cl.duoc.hotel.habitaciones.repository;

import cl.duoc.hotel.habitaciones.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
}
