package cl.duoc.hotel.empleados.service;

import cl.duoc.hotel.empleados.dto.*;
import cl.duoc.hotel.empleados.exception.ResourceNotFoundException;
import cl.duoc.hotel.empleados.model.Empleado;
import cl.duoc.hotel.empleados.repository.EmpleadoRepository;
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
class EmpleadoServiceTest {
    @Mock private EmpleadoRepository repository;
    @InjectMocks private EmpleadoService service;

    @Test
    void crear_exito() {
        // Given
        EmpleadoRequestDTO req = EmpleadoRequestDTO.builder().nombre("test").build();
        Empleado saved = Empleado.builder().id(1L).nombre("test").build();
        when(repository.save(any())).thenReturn(saved);
        // When
        EmpleadoResponseDTO res = service.crear(req);
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
        when(repository.findAll()).thenReturn(List.of(Empleado.builder().id(1L).nombre("a").build()));
        assertThat(service.listar()).hasSize(1);
    }
}
