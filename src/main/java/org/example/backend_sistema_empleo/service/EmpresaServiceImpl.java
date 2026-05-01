package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.EmpresaDto;
import org.example.backend_sistema_empleo.model.Empresa;
import org.example.backend_sistema_empleo.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    private EmpresaDto convertirADto(Empresa e) {
        return new EmpresaDto(
                e.getIdEmpresa(),
                e.getNombre(),
                e.getSector(),
                e.getDescripcion(),
                e.getEmail(),
                e.getTelefono(),
                e.getCiudad()
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
        return convertirADto(empresaRepository.save(convertirAEntity(dto)));
    }

    @Override
    public EmpresaDto actualizar(Long id, EmpresaDto dto) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("No existe la empresa");
        }
        dto.setIdEmpresa(id);
        return guardar(dto);
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
}