package cl.duoc.hotel.notificaciones.repository;

import cl.duoc.hotel.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
