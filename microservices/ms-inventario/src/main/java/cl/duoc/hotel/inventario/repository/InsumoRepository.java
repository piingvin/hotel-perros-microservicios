package cl.duoc.hotel.inventario.repository;

import cl.duoc.hotel.inventario.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
