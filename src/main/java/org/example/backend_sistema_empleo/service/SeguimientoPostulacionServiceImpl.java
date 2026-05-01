package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.SeguimientoPostulacionDto;
import org.example.backend_sistema_empleo.model.SeguimientoPostulacion;
import org.example.backend_sistema_empleo.model.Postulacion;
import org.example.backend_sistema_empleo.repository.SeguimientoPostulacionRepository;
import org.example.backend_sistema_empleo.repository.PostulacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguimientoPostulacionServiceImpl implements SeguimientoPostulacionService {

    private final SeguimientoPostulacionRepository seguimientoRepository;
    private final PostulacionRepository postulacionRepository;

    public SeguimientoPostulacionServiceImpl(SeguimientoPostulacionRepository seguimientoRepository,
                                             PostulacionRepository postulacionRepository) {
        this.seguimientoRepository = seguimientoRepository;
        this.postulacionRepository = postulacionRepository;
    }

    private SeguimientoPostulacionDto convertirADto(SeguimientoPostulacion s) {
        return new SeguimientoPostulacionDto(
                s.getIdSeguimiento(),
                s.getFechaCambio(),
                s.getEstadoAnterior(),
                s.getEstadoNuevo(),
                s.getObservacion(),
                s.getPostulacion() != null ? s.getPostulacion().getIdPostulacion() : null
        );
    }

    private SeguimientoPostulacion convertirAEntity(SeguimientoPostulacionDto dto) {
        SeguimientoPostulacion s = new SeguimientoPostulacion();
        s.setIdSeguimiento(dto.getIdSeguimiento());
        s.setFechaCambio(dto.getFechaCambio());
        s.setEstadoAnterior(dto.getEstadoAnterior());
        s.setEstadoNuevo(dto.getEstadoNuevo());
        s.setObservacion(dto.getObservacion());
        if (dto.getIdPostulacion() != null) {
            Postulacion p = postulacionRepository.findById(dto.getIdPostulacion())
                    .orElseThrow(() -> new RuntimeException("Postulacion no encontrada"));
            s.setPostulacion(p);
        }
        return s;
    }

    @Override
    public List<SeguimientoPostulacionDto> listar() {
        return seguimientoRepository.findAll()
                .stream().map(this::convertirADto).collect(Collectors.toList());
    }

    @Override
    public SeguimientoPostulacionDto guardar(SeguimientoPostulacionDto dto) {
        return convertirADto(seguimientoRepository.save(convertirAEntity(dto)));
    }

    @Override
    public SeguimientoPostulacionDto actualizar(Long id, SeguimientoPostulacionDto dto) {
        if (!seguimientoRepository.existsById(id)) throw new RuntimeException("Seguimiento no encontrado");
        dto.setIdSeguimiento(id);
        return guardar(dto);
    }

    @Override
    public void eliminar(Long id) {
        seguimientoRepository.deleteById(id);
    }

    @Override
    public List<SeguimientoPostulacionDto> historialPorPostulacion(Long idPostulacion) {
        return seguimientoRepository.obtenerHistorialPorPostulacion(idPostulacion)
                .stream().map(this::convertirADto).collect(Collectors.toList());
    }
}
