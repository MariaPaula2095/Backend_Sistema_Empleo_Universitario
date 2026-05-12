package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.model.Empresa;
import org.example.backend_sistema_empleo.model.EmpresaPendiente;
import org.example.backend_sistema_empleo.repository.EmpresaPendienteRepository;
import org.example.backend_sistema_empleo.repository.EmpresaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaPendienteServiceImpl implements EmpresaPendienteService {

    private final EmpresaPendienteRepository repo;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpresaPendienteServiceImpl(EmpresaPendienteRepository repo,
                                       EmpresaRepository empresaRepository,
                                       PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
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

        if (emp.getPassword() == null || emp.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        emp.setEstado("PENDIENTE");
        emp.setMensaje("Tu solicitud está en revisión por el administrador");

        return repo.save(emp);
    }

    @Override
    public List<EmpresaPendiente> listar() {
        return repo.findAll();
    }

    @Override
    public EmpresaPendiente aprobar(Long id, String mensaje) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        emp.setEstado("APROBADA");

        // Si el admin escribió mensaje, se guarda
        if (mensaje != null && !mensaje.isBlank()) {
            emp.setMensaje(mensaje);
        } else {
            emp.setMensaje("Empresa aprobada por el administrador");
        }

        // Crear empresa en tabla empresa
        Empresa empresa = new Empresa();
        empresa.setNombre(emp.getNombre());
        empresa.setEmail(emp.getEmail());
        empresa.setPassword(emp.getPassword());

        empresaRepository.save(empresa);

        return repo.save(emp);
    }

    @Override
    public EmpresaPendiente rechazar(Long id, String mensaje) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        emp.setEstado("RECHAZADA");

        // Guardar motivo del rechazo
        if (mensaje != null && !mensaje.isBlank()) {
            emp.setMensaje(mensaje);
        } else {
            emp.setMensaje("Empresa rechazada por el administrador");
        }

        return repo.save(emp);
    }
}