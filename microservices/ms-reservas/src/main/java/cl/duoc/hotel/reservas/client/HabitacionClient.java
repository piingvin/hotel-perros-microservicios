package cl.duoc.hotel.reservas.client;

import cl.duoc.hotel.reservas.dto.HabitacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-habitaciones", url = "http://ms-habitaciones:8084/api/v1/habitaciones")
public interface HabitacionClient {

    @GetMapping("/{id}")
    HabitacionClientDTO obtenerHabitacion(@PathVariable("id") Long id);
}