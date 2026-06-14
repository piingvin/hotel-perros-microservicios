package cl.duoc.hotel.mascotas.repository;

import cl.duoc.hotel.mascotas.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
