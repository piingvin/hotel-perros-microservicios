package cl.duoc.hotel.habitaciones.service;

import cl.duoc.hotel.habitaciones.dto.*;
import cl.duoc.hotel.habitaciones.exception.ResourceNotFoundException;
import cl.duoc.hotel.habitaciones.model.Habitacion;
import cl.duoc.hotel.habitaciones.repository.HabitacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {
    @Mock private HabitacionRepository repository;
    @InjectMocks private HabitacionService service;

    @Test
    void crear_exito() {
        // Given (Con todos los datos reales)
        HabitacionRequestDTO req = HabitacionRequestDTO.builder()
                .tipo("VIP").numero("101").precioNoche(50000.0).estado("DISPONIBLE").build();
        Habitacion saved = Habitacion.builder()
                .id(1L).tipo("VIP").numero("101").precioNoche(50000.0).estado("DISPONIBLE").build();

        when(repository.save(any())).thenReturn(saved);

        // When
        HabitacionResponseDTO res = service.crear(req);

        // Then
        assertThat(res.getId()).isEqualTo(1L);
        assertThat(res.getPrecioNoche()).isEqualTo(50000.0);
    }

    @Test
    void obtener_noEncontrado() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtener(9L)).isInstanceOf(ResourceNotFoundException.class);
    }
}