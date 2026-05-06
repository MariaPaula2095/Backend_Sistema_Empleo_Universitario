package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.dto.UsuarioDto;
import org.example.backend_sistema_empleo.service.UsuarioService;
import org.example.backend_sistema_empleo.configuration.JwtUtil;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    // 👑 SOLO ADMIN
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // 🔓 público
    @PostMapping("/guardar")
    public ResponseEntity<UsuarioDto> guardar(@Valid @RequestBody UsuarioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.guardar(dto));
    }

    // 🎓 SOLO EL MISMO USUARIO (STUDENT) O ADMIN
    @PatchMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ESTUDIANTE')")
    public ResponseEntity<UsuarioDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDto dto) {

        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    // 🔐 login público
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioDto dto) {

        UsuarioDto usuario = usuarioService.login(dto.getEmail(), dto.getPassword());

        String token = jwtUtil.generateToken(
                usuario.getEmail(),
                usuario.getTipoUsuario().name()
        );

        return ResponseEntity.ok(Map.of(
                "token", token,
                "usuario", usuario
        ));
    }

    // 👑 SOLO ADMIN
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 🎓 el estudiante SOLO puede ver SU correo
    @GetMapping("/buscar-email")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #email")
    public ResponseEntity<UsuarioDto> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }
    @PostMapping("/recuperar-password")
    public ResponseEntity<?> recuperarPassword(@RequestParam String email) {

        String nuevaPass = usuarioService.recuperarPassword(email);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Contraseña generada correctamente",
                "passwordTemporal", nuevaPass
        ));
    }
}