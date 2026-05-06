package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.EmpresaDto;
import org.example.backend_sistema_empleo.model.Empresa;
import org.example.backend_sistema_empleo.repository.EmpresaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository,
                              PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private EmpresaDto convertirADto(Empresa e) {
        return new EmpresaDto(
                e.getIdEmpresa(),
                e.getNombre(),
                e.getSector(),
                e.getDescripcion(),
                e.getEmail(),
                e.getTelefono(),
                e.getCiudad(),
                null
        );
    }

    private Empresa convertirAEntity(EmpresaDto dto) {
        Empresa e = new Empresa();
        e.setIdEmpresa(dto.getIdEmpresa());
        e.setNombre(dto.getNombre());
        e.setSector(dto.getSector());
        e.setDescripcion(dto.getDescripcion());
        e.setEmail(dto.getEmail());
        e.setTelefono(dto.getTelefono());
        e.setCiudad(dto.getCiudad());
        e.setPassword(dto.getPassword());

        return e;
    }

    @Override
    public List<EmpresaDto> listar() {
        return empresaRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public EmpresaDto guardar(EmpresaDto dto) {

        Empresa empresa = convertirAEntity(dto);

        // 🔐 password obligatoria en creación
        if (empresa.getPassword() != null) {
            empresa.setPassword(passwordEncoder.encode(empresa.getPassword()));
        }

        return convertirADto(empresaRepository.save(empresa));
    }

    @Override
    public EmpresaDto actualizar(Long id, EmpresaDto dto) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la empresa"));

        empresa.setNombre(dto.getNombre());
        empresa.setSector(dto.getSector());
        empresa.setDescripcion(dto.getDescripcion());
        empresa.setEmail(dto.getEmail());
        empresa.setTelefono(dto.getTelefono());
        empresa.setCiudad(dto.getCiudad());

        return convertirADto(empresaRepository.save(empresa));
    }

    @Override
    public void eliminar(Long id) {
        empresaRepository.deleteById(id);
    }

    @Override
    public List<EmpresaDto> listarEmpresasConMasOfertas() {
        return empresaRepository.listarEmpresasConMasOfertas()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public EmpresaDto login(String email, String password) {

        Empresa emp = empresaRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        if (!passwordEncoder.matches(password, emp.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return convertirADto(emp);
    }
}