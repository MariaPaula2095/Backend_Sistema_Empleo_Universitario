package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.model.EmpresaPendiente;
import org.example.backend_sistema_empleo.repository.EmpresaPendienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaPendienteServiceImpl implements EmpresaPendienteService {

    private final EmpresaPendienteRepository repo;

    public EmpresaPendienteServiceImpl(EmpresaPendienteRepository repo) {
        this.repo = repo;
    }

    private void validarEmail(String email) {

        if (email == null) {
            throw new IllegalArgumentException("Email no puede ser nulo");
        }

        String regex =
                "^[A-Za-z0-9._%+-]+@(?!gmail\\.com$|hotmail\\.com$|outlook\\.com$)[A-Za-z0-9.-]+\\.(com|co|net)$";

        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Solo correos empresariales (.com, .co, .net)");
        }
    }

    @Override
    public EmpresaPendiente crear(EmpresaPendiente emp) {

        validarEmail(emp.getEmail());

        emp.setEstado("PENDIENTE");
        emp.setMensaje("En revisión por el administrador");

        return repo.save(emp);
    }

    @Override
    public List<EmpresaPendiente> listar() {
        return repo.findAll();
    }

    @Override
    public EmpresaPendiente aprobar(Long id) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        emp.setEstado("APROBADA");
        emp.setMensaje("Empresa aprobada por el administrador");

        return repo.save(emp);
    }

    @Override
    public EmpresaPendiente rechazar(Long id) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        emp.setEstado("RECHAZADA");
        emp.setMensaje("Empresa rechazada por el administrador");

        return repo.save(emp);
    }
}