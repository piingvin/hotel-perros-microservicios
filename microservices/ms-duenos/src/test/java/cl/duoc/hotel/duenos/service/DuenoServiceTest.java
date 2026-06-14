package cl.duoc.hotel.duenos.service;

import cl.duoc.hotel.duenos.dto.*;
import cl.duoc.hotel.duenos.exception.ResourceNotFoundException;
import cl.duoc.hotel.duenos.model.Dueno;
import cl.duoc.hotel.duenos.repository.DuenoRepository;
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
class DuenoServiceTest {
    @Mock private DuenoRepository repository;
    @InjectMocks private DuenoService service;

    @Test
    void crear_exito() {
        // Given
        DuenoRequestDTO req = DuenoRequestDTO.builder().nombreCompleto("test").build();
        Dueno saved = Dueno.builder().id(1L).nombreCompleto("test").build();
        when(repository.save(any())).thenReturn(saved);
        // When
        DuenoResponseDTO res = service.crear(req);
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
        when(repository.findAll()).thenReturn(List.of(Dueno.builder().id(1L).nombreCompleto("a").build()));
        assertThat(service.listar()).hasSize(1);
    }
}
