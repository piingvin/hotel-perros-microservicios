package cl.duoc.hotel.mascotas.service;

import cl.duoc.hotel.mascotas.dto.*;
import cl.duoc.hotel.mascotas.exception.ResourceNotFoundException;
import cl.duoc.hotel.mascotas.model.Mascota;
import cl.duoc.hotel.mascotas.repository.MascotaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {
    @Mock private MascotaRepository repository;
    @InjectMocks private MascotaService service;

    @Test
    void crear_exito() {
        // Given
        MascotaRequestDTO req = MascotaRequestDTO.builder()
                .nombre("Rex").raza("Pastor").edad(3).peso(12.5).vacunasAlDia(true).duenoId(1L).build();
        Mascota saved = Mascota.builder()
                .id(1L).nombre("Rex").raza("Pastor").edad(3).peso(12.5).vacunasAlDia(true).duenoId(1L).build();

        when(repository.save(any())).thenReturn(saved);

        // When
        MascotaResponseDTO res = service.crear(req);

        // Then
        assertThat(res.getId()).isEqualTo(1L);
        assertThat(res.getNombre()).isEqualTo("Rex");
    }

    @Test
    void obtener_noEncontrado() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtener(9L)).isInstanceOf(ResourceNotFoundException.class);
    }
}