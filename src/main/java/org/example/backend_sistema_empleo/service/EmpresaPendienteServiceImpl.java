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

        EmpresaPendiente existente = repo.findByEmail(emp.getEmail())
                .orElse(null);

        // =========================
        // SI YA EXISTE → ACTUALIZA
        // =========================
        if (existente != null) {

            if (emp.getNombre() != null && !emp.getNombre().isBlank())
                existente.setNombre(emp.getNombre());

            if (emp.getPassword() != null && !emp.getPassword().isBlank())
                existente.setPassword(passwordEncoder.encode(emp.getPassword()));

            if (emp.getSector() != null && !emp.getSector().isBlank())
                existente.setSector(emp.getSector());

            if (emp.getTelefono() != null && !emp.getTelefono().isBlank())
                existente.setTelefono(emp.getTelefono());

            if (emp.getCiudad() != null && !emp.getCiudad().isBlank())
                existente.setCiudad(emp.getCiudad());

            if (emp.getDescripcion() != null && !emp.getDescripcion().isBlank())
                existente.setDescripcion(emp.getDescripcion());

            existente.setEstado("PENDIENTE");
            existente.setMensaje("Tu solicitud está en revisión por el administrador");
            existente.setActivo(true);

            return repo.save(existente);
        }

        // =========================
        // SI NO EXISTE → CREA NUEVO
        // =========================
        if (emp.getPassword() == null || emp.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        emp.setEstado("PENDIENTE");
        emp.setMensaje("Tu solicitud está en revisión por el administrador");
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
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        if ("APROBADA".equals(emp.getEstado())) {
            throw new RuntimeException("La empresa ya fue aprobada");
        }

        emp.setEstado("APROBADA");
        emp.setActivo(false);

        if (mensaje != null && !mensaje.isBlank()) {
            emp.setMensaje(mensaje);
        } else {
            emp.setMensaje("Empresa aprobada por el administrador");
        }

        Empresa empresaExistente = empresaRepository
                .findByEmail(emp.getEmail())
                .orElse(null);

        if (empresaExistente == null) {
            // CREAR EMPRESA CON TODOS LOS CAMPOS
            Empresa empresa = new Empresa();
            empresa.setNombre(emp.getNombre());
            empresa.setEmail(emp.getEmail());
            empresa.setPassword(emp.getPassword());
            empresa.setSector(emp.getSector());
            empresa.setTelefono(emp.getTelefono());
            empresa.setCiudad(emp.getCiudad());
            empresa.setDescripcion(emp.getDescripcion());
            empresaRepository.save(empresa);
        } else {
            // SI YA EXISTE → ACTUALIZAR CAMPOS DEL PERFIL
            empresaExistente.setSector(emp.getSector());
            empresaExistente.setTelefono(emp.getTelefono());
            empresaExistente.setCiudad(emp.getCiudad());
            empresaExistente.setDescripcion(emp.getDescripcion());
            empresaRepository.save(empresaExistente);
        }

        return repo.save(emp);
    }

    @Override
    public String rechazar(Long id, String mensaje) {

        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Integer rechazosActuales = emp.getRechazos() != null ? emp.getRechazos() : 0;
        rechazosActuales++;

        if (rechazosActuales >= 3) {
            repo.delete(emp);
            return "Empresa eliminada por alcanzar 3 rechazos";
        }

        emp.setRechazos(rechazosActuales);
        emp.setEstado("RECHAZADA");

        if (mensaje != null && !mensaje.isBlank()) {
            emp.setMensaje(mensaje);
        } else {
            emp.setMensaje("Empresa rechazada por el administrador");
        }

        repo.save(emp);

        return "Empresa rechazada correctamente. Intento " + rechazosActuales + " de 3";
    }

    @Override
    public void eliminarEmpresa(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        empresaRepository.delete(empresa);
    }

    @Override
    public void eliminarPendiente(Long id) {
        EmpresaPendiente emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        repo.delete(emp);
    }

    @Override
    public EmpresaPendiente actualizar(EmpresaPendiente emp) {

        if (emp.getEmail() == null || emp.getEmail().isBlank()) {
            throw new RuntimeException("El email es obligatorio");
        }

        EmpresaPendiente existente = repo.findByEmail(emp.getEmail())
                .orElseThrow(() -> new RuntimeException("No existe una solicitud con ese email"));

        // SOLO ACTUALIZA CAMPOS ENVIADOS
        if (emp.getNombre() != null && !emp.getNombre().isBlank())
            existente.setNombre(emp.getNombre());
        if (emp.getSector() != null && !emp.getSector().isBlank())
            existente.setSector(emp.getSector());
        if (emp.getTelefono() != null && !emp.getTelefono().isBlank())
            existente.setTelefono(emp.getTelefono());
        if (emp.getCiudad() != null && !emp.getCiudad().isBlank())
            existente.setCiudad(emp.getCiudad());
        if (emp.getDescripcion() != null && !emp.getDescripcion().isBlank())
            existente.setDescripcion(emp.getDescripcion());
        if (emp.getPassword() != null && !emp.getPassword().isBlank())
            existente.setPassword(passwordEncoder.encode(emp.getPassword()));

        existente.setEstado("PENDIENTE");
        existente.setMensaje("Tu solicitud fue actualizada y está nuevamente en revisión");
        existente.setActivo(true);

        return repo.save(existente);
    }
}