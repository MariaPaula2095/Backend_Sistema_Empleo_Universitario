package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.model.EmpresaPendiente;
import org.example.backend_sistema_empleo.service.EmpresaPendienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas-pendientes")
public class EmpresaPendienteController {

    private final EmpresaPendienteService service;

    public EmpresaPendienteController(EmpresaPendienteService service) {
        this.service = service;
    }

    // EMPRESA ENVÍA SOLICITUD
    @PostMapping("/enviar")
    public EmpresaPendiente enviar(@RequestBody EmpresaPendiente emp) {
        return service.crear(emp);
    }

    // EMPRESA ACTUALIZA SOLICITUD
    @PutMapping("/actualizar")
    public EmpresaPendiente actualizar(
            @RequestBody EmpresaPendiente emp
    ) {
        return service.actualizar(emp);
    }

    // ADMIN
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmpresaPendiente> listar() {
        return service.listar();
    }

    // ADMIN APRUEBA
    @PutMapping("/aprobar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmpresaPendiente aprobar(
            @PathVariable Long id,
            @RequestParam(required = false) String mensaje
    ) {
        return service.aprobar(id, mensaje);
    }

    // ADMIN RECHAZA
    @PutMapping("/rechazar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rechazar(
            @PathVariable Long id,
            @RequestParam(required = false) String mensaje
    ) {

        String respuesta = service.rechazar(id, mensaje);

        return ResponseEntity.ok(respuesta);
    }

    // ADMIN ELIMINA EMPRESA
    @DeleteMapping("/empresa/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarEmpresa(@PathVariable Long id) {
        service.eliminarEmpresa(id);
    }

    // ADMIN ELIMINA SOLICITUD PENDIENTE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarPendiente(@PathVariable Long id) {
        service.eliminarPendiente(id);
    }
}