package cl.duoc.hotel.empleados.repository;

import cl.duoc.hotel.empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
