package cl.duoc.hotel.facturacion.service;

import cl.duoc.hotel.facturacion.client.ReservaClient;
import cl.duoc.hotel.facturacion.dto.*;
import cl.duoc.hotel.facturacion.exception.ResourceNotFoundException;
import cl.duoc.hotel.facturacion.model.Factura;
import cl.duoc.hotel.facturacion.repository.FacturaRepository;
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
class FacturaServiceTest {

    @Mock
    private FacturaRepository repository;

    // 1. Agregamos el Mock del FeignClient para simular la red
    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private FacturaService service;

    @Test
    void crear_exito() {
        // Given (Preparamos datos con los campos obligatorios)
        FacturaRequestDTO req = FacturaRequestDTO.builder()
                .concepto("test")
                .reservaId(5L)
                .monto(1000.0)
                .metodoPago("Efectivo")
                .build();

        Factura saved = Factura.builder()
                .id(1L)
                .concepto("test")
                .reservaId(5L)
                .build();

        // 2. Simulamos que el microservicio de reservas responde correctamente
        when(reservaClient.obtenerReserva(5L)).thenReturn(new Object());

        // Simulamos el guardado en base de datos
        when(repository.save(any())).thenReturn(saved);

        // When
        FacturaResponseDTO res = service.crear(req);

        // Then
        assertThat(res.getId()).isEqualTo(1L);
        verify(reservaClient, times(1)).obtenerReserva(5L); // Verificamos que sí se llamó al otro MS
    }

    @Test
    void obtener_noEncontrado() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtener(9L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void listar_retornaElementos() {
        when(repository.findAll()).thenReturn(List.of(
                Factura.builder().id(1L).concepto("a").build()
        ));
        assertThat(service.listar()).hasSize(1);
    }
}