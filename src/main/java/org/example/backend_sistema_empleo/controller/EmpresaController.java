package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.configuration.JwtUtil;
import org.example.backend_sistema_empleo.dto.EmpresaDto;
import org.example.backend_sistema_empleo.model.Empresa;
import org.example.backend_sistema_empleo.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(originPatterns = "*")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public EmpresaController(EmpresaService empresaService,
                             JwtUtil jwtUtil,
                             PasswordEncoder passwordEncoder) {
        this.empresaService = empresaService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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

    // 🔐 LOGIN EMPRESA
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Empresa empresa) {

        Empresa emp = empresaService.login(
                empresa.getEmail(),
                empresa.getPassword()
        );

        String token = jwtUtil.generateToken(
                emp.getEmail(),
                emp.getRol().name()
        );

        return ResponseEntity.ok(Map.of(
                "token", token,
                "empresa", emp
        ));
    }
}