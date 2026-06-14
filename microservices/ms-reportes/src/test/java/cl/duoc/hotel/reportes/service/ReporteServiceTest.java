package cl.duoc.hotel.reportes.service;

import cl.duoc.hotel.reportes.dto.*;
import cl.duoc.hotel.reportes.exception.ResourceNotFoundException;
import cl.duoc.hotel.reportes.model.Reporte;
import cl.duoc.hotel.reportes.repository.ReporteRepository;
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
class ReporteServiceTest {
    @Mock private ReporteRepository repository;
    @InjectMocks private ReporteService service;

    @Test
    void crear_exito() {
        // Given
        ReporteRequestDTO req = ReporteRequestDTO.builder().titulo("test").build();
        Reporte saved = Reporte.builder().id(1L).titulo("test").build();
        when(repository.save(any())).thenReturn(saved);
        // When
        ReporteResponseDTO res = service.crear(req);
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
        when(repository.findAll()).thenReturn(List.of(Reporte.builder().id(1L).titulo("a").build()));
        assertThat(service.listar()).hasSize(1);
    }
}
