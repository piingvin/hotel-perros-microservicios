package cl.duoc.hotel.facturacion.repository;

import cl.duoc.hotel.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
