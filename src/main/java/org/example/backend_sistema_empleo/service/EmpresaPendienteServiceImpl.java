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

        EmpresaPendiente existente = repo.findByEmail(emp.getEmail())
                .orElse(null);

        // SI YA EXISTE → ACTUALIZA
        if (existente != null) {

            existente.setNombre(emp.getNombre());

            existente.setPassword(
                    passwordEncoder.encode(emp.getPassword())
            );

            existente.setEstado("PENDIENTE");

            existente.setMensaje(
                    "Tu solicitud está en revisión por el administrador"
            );

            // IMPORTANTE:
            // NO BORRAR RECHAZOS
            // NO CAMBIAR ACTIVO

            return repo.save(existente);
        }

        // SI NO EXISTE → CREA NUEVO
        emp.setPassword(
                passwordEncoder.encode(emp.getPassword())
        );

        emp.setEstado("PENDIENTE");

        emp.setMensaje(
                "Tu solicitud está en revisión por el administrador"
        );

        emp.setRechazos(0);

        emp.setActivo(true);

        return repo.save(emp);
    }

    @Override
    public List<EmpresaPendiente> listar() {
        return repo.findAll();
    }

    @Override
    public EmpresaPendiente aprobar(Long id, String mensaje) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Empresa no encontrada"));

        emp.setEstado("APROBADA");

        // DESACTIVAR SOLICITUD
        emp.setActivo(false);

        if (mensaje != null && !mensaje.isBlank()) {
            emp.setMensaje(mensaje);
        } else {
            emp.setMensaje("Empresa aprobada por el administrador");
        }

        // VALIDAR SI YA EXISTE EMPRESA
        Empresa empresaExistente = empresaRepository
                .findByEmail(emp.getEmail())
                .orElse(null);

        // SOLO CREAR SI NO EXISTE
        if (empresaExistente == null) {

            Empresa empresa = new Empresa();

            empresa.setNombre(emp.getNombre());

            empresa.setEmail(emp.getEmail());

            empresa.setPassword(emp.getPassword());

            empresaRepository.save(empresa);
        }

        return repo.save(emp);
    }

    @Override
    public EmpresaPendiente rechazar(Long id, String mensaje) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Empresa no encontrada"));

        emp.setEstado("RECHAZADA");

        // SUMAR RECHAZOS
        emp.setRechazos(emp.getRechazos() + 1);

        if (mensaje != null && !mensaje.isBlank()) {
            emp.setMensaje(mensaje);
        } else {
            emp.setMensaje("Empresa rechazada por el administrador");
        }

        return repo.save(emp);
    }

    @Override
    public void eliminarEmpresa(Long id) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Empresa no encontrada"));

        empresaRepository.delete(empresa);
    }
    @Override
    public void eliminarPendiente(Long id) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Solicitud no encontrada"));

        repo.delete(emp);
    }

}