package cl.duoc.hotel.facturacion.client;

import cl.duoc.hotel.facturacion.dto.FacturaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas", url = "http://ms-reservas:8080/api/v1/reservas")
public interface ReservaClient {

    @GetMapping("/{id}")
    Object obtenerReserva(@PathVariable("id") Long id); // Cambia Object por el DTO correcto que uses
}