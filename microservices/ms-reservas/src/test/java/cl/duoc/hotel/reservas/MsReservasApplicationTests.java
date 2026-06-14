package cl.duoc.hotel.reservas.service;

import cl.duoc.hotel.reservas.client.HabitacionClient;
import cl.duoc.hotel.reservas.dto.HabitacionClientDTO;
import cl.duoc.hotel.reservas.dto.ReservaRequestDTO;
import cl.duoc.hotel.reservas.dto.ReservaResponseDTO;
import cl.duoc.hotel.reservas.exception.ResourceNotFoundException;
import cl.duoc.hotel.reservas.model.Reserva;
import cl.duoc.hotel.reservas.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

	@Mock private ReservaRepository repository;
	@Mock private HabitacionClient habitacionClient; // Simulamos la red
	@InjectMocks private ReservaService service;

	@Test
	void calcularCostoReserva_exito() {
		// Given
		Reserva reserva = Reserva.builder()
				.id(1L).habitacionId(5L)
				.fechaInicio(LocalDate.now())
				.fechaFin(LocalDate.now().plusDays(3))
				.build();

		HabitacionClientDTO habitacionMock = new HabitacionClientDTO();
		habitacionMock.setPrecioNoche(25000.0);

		when(repository.findById(1L)).thenReturn(Optional.of(reserva));
		when(habitacionClient.obtenerHabitacion(5L)).thenReturn(habitacionMock);

		// When
		Double costoTotal = service.calcularCostoReserva(1L);

		// Then
		assertThat(costoTotal).isEqualTo(75000.0); // 3 días x 25.000
		verify(habitacionClient, times(1)).obtenerHabitacion(5L);
	}
}