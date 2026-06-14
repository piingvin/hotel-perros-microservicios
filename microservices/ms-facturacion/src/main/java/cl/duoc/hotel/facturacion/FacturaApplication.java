package cl.duoc.hotel.facturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FacturaApplication {
    public static void main(String[] args) {
        SpringApplication.run(FacturaApplication.class, args);
    }
}
