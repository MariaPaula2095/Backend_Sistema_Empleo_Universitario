package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.PostulacionDto;
import org.example.backend_sistema_empleo.service.PostulacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<PostulacionDto>> listar() {
        return ResponseEntity.ok(postulacionService.listar());
    }

    @PostMapping("/guardar")
    public ResponseEntity<PostulacionDto> guardar(@RequestBody PostulacionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postulacionService.guardar(dto));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PostulacionDto> actualizar(@PathVariable Long id, @RequestBody PostulacionDto dto) {
        return ResponseEntity.ok(postulacionService.actualizar(id, dto));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        postulacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Consultas nativas
    @GetMapping("/candidato/{idUsuario}")
    public ResponseEntity<List<PostulacionDto>> listarPorCandidato(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(postulacionService.listarPorCandidato(idUsuario));
    }

    @GetMapping("/oferta/{idOferta}")
    public ResponseEntity<List<PostulacionDto>> listarCandidatosPorOferta(@PathVariable Long idOferta) {
        return ResponseEntity.ok(postulacionService.listarCandidatosPorOferta(idOferta));
    }
}
