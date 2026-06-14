package cl.duoc.hotel.duenos.controller;

import cl.duoc.hotel.duenos.dto.*;
import cl.duoc.hotel.duenos.service.DuenoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/duenos")
@RequiredArgsConstructor
@Tag(name = "Dueno", description = "Gestión de dueño")
public class DuenoController {
    private final DuenoService service;

    @GetMapping
    @Operation(summary = "Listar todos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida")
    public ResponseEntity<List<DuenoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    public ResponseEntity<DuenoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Crear registro")
    public ResponseEntity<DuenoResponseDTO> crear(@Valid @RequestBody DuenoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DuenoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DuenoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
