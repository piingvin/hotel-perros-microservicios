package cl.duoc.hotel.notificaciones.service;

import cl.duoc.hotel.notificaciones.dto.*;
import cl.duoc.hotel.notificaciones.exception.ResourceNotFoundException;
import cl.duoc.hotel.notificaciones.model.Notificacion;
import cl.duoc.hotel.notificaciones.repository.NotificacionRepository;
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
class NotificacionServiceTest {
    @Mock private NotificacionRepository repository;
    @InjectMocks private NotificacionService service;

    @Test
    void crear_exito() {
        // Given
        NotificacionRequestDTO req = NotificacionRequestDTO.builder().mensaje("test").build();
        Notificacion saved = Notificacion.builder().id(1L).mensaje("test").build();
        when(repository.save(any())).thenReturn(saved);
        // When
        NotificacionResponseDTO res = service.crear(req);
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
        when(repository.findAll()).thenReturn(List.of(Notificacion.builder().id(1L).mensaje("a").build()));
        assertThat(service.listar()).hasSize(1);
    }
}
