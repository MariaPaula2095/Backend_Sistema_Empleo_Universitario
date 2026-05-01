package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.PostulacionDto;
import org.example.backend_sistema_empleo.model.Postulacion;
import org.example.backend_sistema_empleo.model.Usuario;
import org.example.backend_sistema_empleo.model.OfertaLaboral;
import org.example.backend_sistema_empleo.repository.PostulacionRepository;
import org.example.backend_sistema_empleo.repository.UsuarioRepository;
import org.example.backend_sistema_empleo.repository.OfertaLaboralRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    private final PostulacionRepository postulacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final OfertaLaboralRepository ofertaLaboralRepository;

    public PostulacionServiceImpl(PostulacionRepository postulacionRepository,
                                  UsuarioRepository usuarioRepository,
                                  OfertaLaboralRepository ofertaLaboralRepository) {
        this.postulacionRepository = postulacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.ofertaLaboralRepository = ofertaLaboralRepository;
    }

    private PostulacionDto convertirADto(Postulacion p) {
        return new PostulacionDto(
                p.getIdPostulacion(),
                p.getFechaPostulacion(),
                p.getEstado(),
                p.getUsuario() != null ? p.getUsuario().getIdUsuario() : null,
                p.getOfertaLaboral() != null ? p.getOfertaLaboral().getIdOferta() : null
        );
    }

    private Postulacion convertirAEntity(PostulacionDto dto) {
        Postulacion p = new Postulacion();
        p.setIdPostulacion(dto.getIdPostulacion());
        p.setFechaPostulacion(dto.getFechaPostulacion());
        p.setEstado(dto.getEstado());
        if (dto.getIdUsuario() != null) {
            Usuario u = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            p.setUsuario(u);
        }
        if (dto.getIdOferta() != null) {
            OfertaLaboral o = ofertaLaboralRepository.findById(dto.getIdOferta())
                    .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
            p.setOfertaLaboral(o);
        }
        return p;
    }

    @Override
    public List<PostulacionDto> listar() {
        return postulacionRepository.findAll()
                .stream().map(this::convertirADto).collect(Collectors.toList());
    }

    @Override
    public PostulacionDto guardar(PostulacionDto dto) {
        return convertirADto(postulacionRepository.save(convertirAEntity(dto)));
    }

    @Override
    public PostulacionDto actualizar(Long id, PostulacionDto dto) {
        if (!postulacionRepository.existsById(id)) throw new RuntimeException("Postulacion no encontrada");
        dto.setIdPostulacion(id);
        return guardar(dto);
    }

    @Override
    public void eliminar(Long id) {
        postulacionRepository.deleteById(id);
    }

    @Override
    public List<PostulacionDto> listarPorCandidato(Long idUsuario) {
        return postulacionRepository.findByUsuario(idUsuario)
                .stream().map(this::convertirADto).collect(Collectors.toList());
    }

    @Override
    public List<PostulacionDto> listarCandidatosPorOferta(Long idOferta) {
        return postulacionRepository.findCandidatosByOferta(idOferta)
                .stream().map(this::convertirADto).collect(Collectors.toList());
    }
}
