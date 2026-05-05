package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.PostulacionDto;
import java.util.List;

public interface PostulacionService {

    List<PostulacionDto> listar();

    PostulacionDto guardar(PostulacionDto dto);

    PostulacionDto actualizar(Long id, PostulacionDto dto);

    void eliminar(Long id);

    List<PostulacionDto> listarPorUsuario(Long idUsuario);

    List<PostulacionDto> listarCandidatosPorOferta(Long idOferta);
}