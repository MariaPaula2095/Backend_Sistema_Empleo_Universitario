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
        if (!perfilRepository.existsById(id)) {
            throw new RuntimeException("No existe el perfil");
        }
        dto.setIdPerfil(id);
        return guardar(dto);
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
}