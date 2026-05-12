package org.example.backend_sistema_empleo.controller;

import org.example.backend_sistema_empleo.model.EmpresaPendiente;
import org.example.backend_sistema_empleo.service.EmpresaPendienteService;
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

    // 🟡 EMPRESA ENVÍA SOLICITUD
    @PostMapping("/enviar")
    public EmpresaPendiente enviar(@RequestBody EmpresaPendiente emp) {
        return service.crear(emp);
    }

    // 👑 ADMIN
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmpresaPendiente> listar() {
        return service.listar();
    }

    // 👑 ADMIN APRUEBA
    @PutMapping("/aprobar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmpresaPendiente aprobar(
            @PathVariable Long id,
            @RequestParam(required = false) String mensaje
    ) {
        return service.aprobar(id, mensaje);
    }

    // 👑 ADMIN RECHAZA
    @PutMapping("/rechazar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmpresaPendiente rechazar(
            @PathVariable Long id,
            @RequestParam(required = false) String mensaje
    ) {
        return service.rechazar(id, mensaje);
    }
}