package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.OfertaLaboralDto;
import org.example.backend_sistema_empleo.model.Empresa;
import org.example.backend_sistema_empleo.model.OfertaLaboral;
import org.example.backend_sistema_empleo.repository.EmpresaRepository;
import org.example.backend_sistema_empleo.repository.OfertaLaboralRepository;
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

        // 🏢 CLAVE: asignar empresa obligatoriamente
        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            o.setEmpresa(empresa);
        }

        return o;
    }


    @Override
    public List<OfertaLaboralDto> listar() {
        return ofertaRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public OfertaLaboralDto guardar(OfertaLaboralDto dto) {

        OfertaLaboral oferta = convertirAEntity(dto);

        if (oferta.getEstado() == null) {
            oferta.setEstado(true);
        }

        return convertirADto(ofertaRepository.save(oferta));
    }

    @Override
    public OfertaLaboralDto actualizar(Long id, OfertaLaboralDto dto) {

        OfertaLaboral oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la oferta laboral"));

        oferta.setTitulo(dto.getTitulo());
        oferta.setDescripcion(dto.getDescripcion());
        oferta.setArea(dto.getArea());
        oferta.setSalario(dto.getSalario());
        oferta.setModalidad(dto.getModalidad());
        oferta.setFechaPublicacion(dto.getFechaPublicacion());
        oferta.setFechaCierre(dto.getFechaCierre());
        oferta.setEstado(dto.getEstado());

        return convertirADto(ofertaRepository.save(oferta));
    }

    @Override
    public void eliminar(Long id) {
        ofertaRepository.deleteById(id);
    }

    // ---------- CONSULTAS ----------

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

    // 🏢 NUEVO: ofertas por empresa
    @Override
    public List<OfertaLaboralDto> listarPorEmpresa(Long idEmpresa) {
        return ofertaRepository.findByEmpresa(idEmpresa)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
}