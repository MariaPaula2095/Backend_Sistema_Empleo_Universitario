package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.PerfilDto;
import org.example.backend_sistema_empleo.model.Perfil;
import org.example.backend_sistema_empleo.model.Usuario;
import org.example.backend_sistema_empleo.repository.PerfilRepository;
import org.example.backend_sistema_empleo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilServiceImpl(PerfilRepository perfilRepository,
                             UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private PerfilDto convertirADto(Perfil p) {
        return new PerfilDto(
                p.getIdPerfil(),
                p.getCarrera(),
                p.getUniversidad(),
                p.getSemestre(),
                p.getHabilidades(),
                p.getExperiencia(),
                p.getCvUrl(),
                p.getDisponibilidad(),
                p.getUsuario() != null ? p.getUsuario().getIdUsuario() : null
        );
    }

    private Perfil convertirAEntity(PerfilDto dto) {
        Perfil p = new Perfil();
        p.setIdPerfil(dto.getIdPerfil());
        p.setCarrera(dto.getCarrera());
        p.setUniversidad(dto.getUniversidad());
        p.setSemestre(dto.getSemestre());
        p.setHabilidades(dto.getHabilidades());
        p.setExperiencia(dto.getExperiencia());
        p.setCvUrl(dto.getCvUrl());
        p.setDisponibilidad(dto.getDisponibilidad());

        if (dto.getIdUsuario() != null) {
            Usuario u = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow();
            p.setUsuario(u);
        }

        return p;
    }

    @Override
    public List<PerfilDto> listar() {
        return perfilRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public PerfilDto guardar(PerfilDto dto) {
        return convertirADto(perfilRepository.save(convertirAEntity(dto)));
    }

    @Override
    public PerfilDto actualizar(Long id, PerfilDto dto) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el perfil"));

        if (dto.getCarrera() != null) perfil.setCarrera(dto.getCarrera());
        if (dto.getUniversidad() != null) perfil.setUniversidad(dto.getUniversidad());
        if (dto.getSemestre() != null) perfil.setSemestre(dto.getSemestre());
        if (dto.getHabilidades() != null) perfil.setHabilidades(dto.getHabilidades());
        if (dto.getExperiencia() != null) perfil.setExperiencia(dto.getExperiencia());
        if (dto.getCvUrl() != null) perfil.setCvUrl(dto.getCvUrl());
        if (dto.getDisponibilidad() != null) perfil.setDisponibilidad(dto.getDisponibilidad());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            perfil.setUsuario(usuario);
        }

        return convertirADto(perfilRepository.save(perfil));
    }

    @Override
    public void eliminar(Long id) {
        perfilRepository.deleteById(id);
    }

    @Override
    public List<PerfilDto> buscarPorCarrera(String carrera) {
        return perfilRepository.buscarPorCarrera(carrera)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PerfilDto> buscarPorHabilidad(String habilidad) {
        return perfilRepository.buscarPorHabilidad(habilidad)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public PerfilDto buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Perfil perfil = perfilRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return convertirADto(perfil);
    }
}