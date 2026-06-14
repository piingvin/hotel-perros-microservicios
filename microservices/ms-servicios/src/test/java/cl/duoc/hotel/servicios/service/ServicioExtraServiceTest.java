package cl.duoc.hotel.servicios.service;

import cl.duoc.hotel.servicios.dto.*;
import cl.duoc.hotel.servicios.exception.ResourceNotFoundException;
import cl.duoc.hotel.servicios.model.ServicioExtra;
import cl.duoc.hotel.servicios.repository.ServicioExtraRepository;
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
class ServicioExtraServiceTest {
    @Mock private ServicioExtraRepository repository;
    @InjectMocks private ServicioExtraService service;

    @Test
    void crear_exito() {
        // Given
        ServicioExtraRequestDTO req = ServicioExtraRequestDTO.builder().descripcion("test").build();
        ServicioExtra saved = ServicioExtra.builder().id(1L).descripcion("test").build();
        when(repository.save(any())).thenReturn(saved);
        // When
        ServicioExtraResponseDTO res = service.crear(req);
        // Then
        assertThat(res.getId()).isEqualTo(1L);
    }

    @Test
    void obtener_noEncontrado() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtener(9L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void listar_retornaElementos() {
        when(repository.findAll()).thenReturn(List.of(ServicioExtra.builder().id(1L).descripcion("a").build()));
        assertThat(service.listar()).hasSize(1);
    }
}
