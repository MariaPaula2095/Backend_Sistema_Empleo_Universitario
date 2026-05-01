package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.PerfilDto;
import org.example.backend_sistema_empleo.service.PerfilService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
@CrossOrigin(originPatterns = "*")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping("/listar")
    public List<PerfilDto> listar() {
        return perfilService.listar();
    }

    @PostMapping("/guardar")
    public PerfilDto guardar(@RequestBody PerfilDto dto) {
        return perfilService.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    public PerfilDto actualizar(@PathVariable Long id, @RequestBody PerfilDto dto) {
        return perfilService.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        perfilService.eliminar(id);
    }

    @GetMapping("/carrera/{carrera}")
    public List<PerfilDto> buscarPorCarrera(@PathVariable String carrera) {
        return perfilService.buscarPorCarrera(carrera);
    }

    @GetMapping("/habilidad/{habilidad}")
    public List<PerfilDto> buscarPorHabilidad(@PathVariable String habilidad) {
        return perfilService.buscarPorHabilidad(habilidad);
    }
}