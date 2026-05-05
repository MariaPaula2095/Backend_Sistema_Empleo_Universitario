package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.PostulacionDto;
import org.example.backend_sistema_empleo.model.Postulacion;
import org.example.backend_sistema_empleo.model.Usuario;
import org.example.backend_sistema_empleo.model.OfertaLaboral;
import org.example.backend_sistema_empleo.model.EstadoPostulacion;
import org.example.backend_sistema_empleo.repository.PostulacionRepository;
import org.example.backend_sistema_empleo.repository.UsuarioRepository;
import org.example.backend_sistema_empleo.repository.OfertaLaboralRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    // ===================== DTO =====================

    private PostulacionDto convertirADto(Postulacion p) {
        return new PostulacionDto(
                p.getIdPostulacion(),
                p.getFechaPostulacion(),
                p.getEstado(),
                p.getUsuario() != null ? p.getUsuario().getIdUsuario() : null,
                p.getOfertaLaboral() != null ? p.getOfertaLaboral().getIdOferta() : null
        );
    }

    // ===================== ENTITY =====================

    private Postulacion convertirAEntity(PostulacionDto dto) {

        Postulacion p = new Postulacion();

        p.setIdPostulacion(dto.getIdPostulacion());
        p.setFechaPostulacion(
                dto.getFechaPostulacion() != null ? dto.getFechaPostulacion() : LocalDate.now()
        );

        // 🔐 SI NO VIENE ESTADO, SIEMPRE PENDIENTE
        p.setEstado(
                dto.getEstado() != null ? dto.getEstado() : EstadoPostulacion.PENDIENTE
        );

        // 👤 USUARIO
        if (dto.getIdUsuario() != null) {
            Usuario u = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            p.setUsuario(u);
        }

        // 🏢 OFERTA
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
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    // 🔥 AQUÍ VA LA REGLA IMPORTANTE (SOLO 1 POSTULACIÓN)
    @Override
    public PostulacionDto guardar(PostulacionDto dto) {

        if (dto.getIdUsuario() != null && dto.getIdOferta() != null) {

            boolean existe = postulacionRepository
                    .existePostulacion(dto.getIdUsuario(), dto.getIdOferta())
                    .isPresent();

            if (existe) {
                throw new RuntimeException("Ya estás postulado a esta oferta");
            }
        }

        return convertirADto(
                postulacionRepository.save(convertirAEntity(dto))
        );
    }

    @Override
    public PostulacionDto actualizar(Long id, PostulacionDto dto) {

        Postulacion existente = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        existente.setEstado(dto.getEstado());

        return convertirADto(postulacionRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        postulacionRepository.deleteById(id);
    }

    // ===================== CONSULTAS =====================


    @Override
    public List<PostulacionDto> listarPorUsuario(Long idUsuario) {
        return postulacionRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
    @Override
    public List<PostulacionDto> listarCandidatosPorOferta(Long idOferta) {
        return postulacionRepository.findByOfertaLaboralIdOferta(idOferta)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
}