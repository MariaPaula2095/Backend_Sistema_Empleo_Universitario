package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.configuration.JwtUtil;
import org.example.backend_sistema_empleo.dto.EmpresaDto;
import org.example.backend_sistema_empleo.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final JwtUtil jwtUtil;

    public EmpresaController(EmpresaService empresaService, JwtUtil jwtUtil) {
        this.empresaService = empresaService;
        this.jwtUtil = jwtUtil;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmpresaDto dto) {

        try {

            EmpresaDto empresa = empresaService.login(
                    dto.getEmail(),
                    dto.getPassword()
            );

            String token = jwtUtil.generateToken(
                    empresa.getEmail(),
                    "EMPRESA"
            );

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "empresa", empresa
            ));

        } catch (Exception e) {

            return ResponseEntity.status(403).body(Map.of(
                    "error", "Forbidden",
                    "message", e.getMessage()
            ));
        }
    }
}