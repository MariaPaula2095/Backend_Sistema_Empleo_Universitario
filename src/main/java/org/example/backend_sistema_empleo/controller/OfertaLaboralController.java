package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.OfertaLaboralDto;
import org.example.backend_sistema_empleo.service.OfertaLaboralService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaLaboralController {

    private final OfertaLaboralService ofertaService;

    public OfertaLaboralController(OfertaLaboralService ofertaService) {
        this.ofertaService = ofertaService;
    }

    // 👤 todos ven
    @GetMapping("/listar")
    public List<OfertaLaboralDto> listar() {
        return ofertaService.listar();
    }

    // 🏢 SOLO EMPRESA Y ADMIN PUBLICAN
    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRESA')")
    public OfertaLaboralDto guardar(@RequestBody OfertaLaboralDto dto) {
        return ofertaService.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRESA')")
    public OfertaLaboralDto actualizar(@PathVariable Long id, @RequestBody OfertaLaboralDto dto) {
        return ofertaService.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRESA')")
    public void eliminar(@PathVariable Long id) {
        ofertaService.eliminar(id);
    }

    @GetMapping("/activas")
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE','EMPRESA')")
    public List<OfertaLaboralDto> listarActivas() {
        return ofertaService.listarOfertasActivas();
    }
}