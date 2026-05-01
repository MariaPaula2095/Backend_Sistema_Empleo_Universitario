package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.OfertaLaboralDto;
import org.example.backend_sistema_empleo.service.OfertaLaboralService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(originPatterns = "*")
public class OfertaLaboralController {

    private final OfertaLaboralService ofertaService;

    public OfertaLaboralController(OfertaLaboralService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping("/listar")
    public List<OfertaLaboralDto> listar() {
        return ofertaService.listar();
    }

    @PostMapping("/guardar")
    public OfertaLaboralDto guardar(@RequestBody OfertaLaboralDto dto) {
        return ofertaService.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    public OfertaLaboralDto actualizar(@PathVariable Long id, @RequestBody OfertaLaboralDto dto) {
        return ofertaService.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        ofertaService.eliminar(id);
    }

    @GetMapping("/area/{area}")
    public List<OfertaLaboralDto> buscarPorArea(@PathVariable String area) {
        return ofertaService.buscarPorArea(area);
    }

    @GetMapping("/cargo/{cargo}")
    public List<OfertaLaboralDto> buscarPorCargo(@PathVariable String cargo) {
        return ofertaService.buscarPorCargo(cargo);
    }

    @GetMapping("/activas")
    public List<OfertaLaboralDto> listarActivas() {
        return ofertaService.listarOfertasActivas();
    }
}