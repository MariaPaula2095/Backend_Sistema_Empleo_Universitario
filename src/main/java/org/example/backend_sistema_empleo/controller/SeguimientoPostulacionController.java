package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.SeguimientoPostulacionDto;
import org.example.backend_sistema_empleo.service.SeguimientoPostulacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoPostulacionController {

    private final SeguimientoPostulacionService seguimientoService;

    public SeguimientoPostulacionController(SeguimientoPostulacionService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SeguimientoPostulacionDto>> listar() {
        return ResponseEntity.ok(seguimientoService.listar());
    }

    @PostMapping("/guardar")
    public ResponseEntity<SeguimientoPostulacionDto> guardar(@RequestBody SeguimientoPostulacionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoService.guardar(dto));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<SeguimientoPostulacionDto> actualizar(@PathVariable Long id, @RequestBody SeguimientoPostulacionDto dto) {
        return ResponseEntity.ok(seguimientoService.actualizar(id, dto));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        seguimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Consulta nativa
    @GetMapping("/historial/{idPostulacion}")
    public ResponseEntity<List<SeguimientoPostulacionDto>> historialPorPostulacion(@PathVariable Long idPostulacion) {
        return ResponseEntity.ok(seguimientoService.historialPorPostulacion(idPostulacion));
    }
}
