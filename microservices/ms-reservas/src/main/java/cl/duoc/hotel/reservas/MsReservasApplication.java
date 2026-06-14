package cl.duoc.hotel.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsReservasApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsReservasApplication.class, args);
	}

}
