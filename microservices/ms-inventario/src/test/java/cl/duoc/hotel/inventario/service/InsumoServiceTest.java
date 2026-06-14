package cl.duoc.hotel.inventario.service;

import cl.duoc.hotel.inventario.dto.*;
import cl.duoc.hotel.inventario.exception.ResourceNotFoundException;
import cl.duoc.hotel.inventario.model.Insumo;
import cl.duoc.hotel.inventario.repository.InsumoRepository;
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
class InsumoServiceTest {
    @Mock private InsumoRepository repository;
    @InjectMocks private InsumoService service;

    @Test
    void crear_exito() {
        // Given
        InsumoRequestDTO req = InsumoRequestDTO.builder().nombre("test").build();
        Insumo saved = Insumo.builder().id(1L).nombre("test").build();
        when(repository.save(any())).thenReturn(saved);
        // When
        InsumoResponseDTO res = service.crear(req);
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
        when(repository.findAll()).thenReturn(List.of(Insumo.builder().id(1L).nombre("a").build()));
        assertThat(service.listar()).hasSize(1);
    }
}
