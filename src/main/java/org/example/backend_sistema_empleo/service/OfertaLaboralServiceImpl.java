package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.OfertaLaboralDto;
import org.example.backend_sistema_empleo.model.OfertaLaboral;
import org.example.backend_sistema_empleo.model.Empresa;
import org.example.backend_sistema_empleo.repository.OfertaLaboralRepository;
import org.example.backend_sistema_empleo.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfertaLaboralServiceImpl implements OfertaLaboralService {

    private final OfertaLaboralRepository ofertaRepository;
    private final EmpresaRepository empresaRepository;

    public OfertaLaboralServiceImpl(OfertaLaboralRepository ofertaRepository,
                                    EmpresaRepository empresaRepository) {
        this.ofertaRepository = ofertaRepository;
        this.empresaRepository = empresaRepository;
    }

    // ---------- Conversión ----------

    private OfertaLaboralDto convertirADto(OfertaLaboral o) {
        return new OfertaLaboralDto(
                o.getIdOferta(),
                o.getTitulo(),
                o.getDescripcion(),
                o.getArea(),
                o.getSalario(),
                o.getModalidad(),
                o.getFechaPublicacion(),
                o.getFechaCierre(),
                o.getEstado(),
                o.getEmpresa() != null ? o.getEmpresa().getIdEmpresa() : null
        );
    }

    private OfertaLaboral convertirAEntity(OfertaLaboralDto dto) {
        OfertaLaboral o = new OfertaLaboral();
        o.setIdOferta(dto.getIdOferta());
        o.setTitulo(dto.getTitulo());
        o.setDescripcion(dto.getDescripcion());
        o.setArea(dto.getArea());
        o.setSalario(dto.getSalario());
        o.setModalidad(dto.getModalidad());
        o.setFechaPublicacion(dto.getFechaPublicacion());
        o.setFechaCierre(dto.getFechaCierre());
        o.setEstado(dto.getEstado());

        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa()).orElseThrow();
            o.setEmpresa(empresa);
        }

        return o;
    }

    // ---------- CRUD ----------

    @Override
    public List<OfertaLaboralDto> listar() {
        return ofertaRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public OfertaLaboralDto guardar(OfertaLaboralDto dto) {
        return convertirADto(ofertaRepository.save(convertirAEntity(dto)));
    }

    @Override
    public OfertaLaboralDto actualizar(Long id, OfertaLaboralDto dto) {
        if (!ofertaRepository.existsById(id)) {
            throw new RuntimeException("No existe la oferta laboral");
        }
        dto.setIdOferta(id);
        return guardar(dto);
    }

    @Override
    public void eliminar(Long id) {
        ofertaRepository.deleteById(id);
    }

    // ---------- Consultas nativas ----------

    @Override
    public List<OfertaLaboralDto> buscarPorArea(String area) {
        return ofertaRepository.findByArea(area)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfertaLaboralDto> buscarPorCargo(String cargo) {
        return ofertaRepository.findByCargo(cargo)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfertaLaboralDto> listarOfertasActivas() {
        return ofertaRepository.findAllActive()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
}