package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.PostulacionDto;
import org.example.backend_sistema_empleo.service.PostulacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionController {

    private final PostulacionService postulacionService;

    public PostulacionController(PostulacionService postulacionService) {
        this.postulacionService = postulacionService;
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PostulacionDto>> listar() {
        return ResponseEntity.ok(postulacionService.listar());
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<PostulacionDto> guardar(@RequestBody PostulacionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postulacionService.guardar(dto));
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ESTUDIANTE')")
    public ResponseEntity<PostulacionDto> actualizar(@PathVariable Long id, @RequestBody PostulacionDto dto) {
        return ResponseEntity.ok(postulacionService.actualizar(id, dto));
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        postulacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/candidato/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #idUsuario.toString()")
    public ResponseEntity<List<PostulacionDto>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(postulacionService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/oferta/{idOferta}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PostulacionDto>> listarCandidatosPorOferta(@PathVariable Long idOferta) {
        return ResponseEntity.ok(postulacionService.listarCandidatosPorOferta(idOferta));
    }
}
