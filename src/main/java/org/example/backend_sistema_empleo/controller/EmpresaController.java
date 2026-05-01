package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.EmpresaDto;
import org.example.backend_sistema_empleo.service.EmpresaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(originPatterns = "*")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/listar")
    public List<EmpresaDto> listar() {
        return empresaService.listar();
    }

    @PostMapping("/guardar")
    public EmpresaDto guardar(@RequestBody EmpresaDto dto) {
        return empresaService.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    public EmpresaDto actualizar(@PathVariable Long id, @RequestBody EmpresaDto dto) {
        return empresaService.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        empresaService.eliminar(id);
    }

    @GetMapping("/top")
    public List<EmpresaDto> listarEmpresasConMasOfertas() {
        return empresaService.listarEmpresasConMasOfertas();
    }
}