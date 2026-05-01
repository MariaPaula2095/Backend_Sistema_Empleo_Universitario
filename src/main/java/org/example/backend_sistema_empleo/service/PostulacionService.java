package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.PostulacionDto;
import java.util.List;

public interface PostulacionService {

    List<PostulacionDto> listar();
    PostulacionDto guardar(PostulacionDto postulacionDto);
    PostulacionDto actualizar(Long id, PostulacionDto postulacionDto);
    void eliminar(Long id);

    // Consultas nativas
    List<PostulacionDto> listarPorCandidato(Long idUsuario);
    List<PostulacionDto> listarCandidatosPorOferta(Long idOferta);
}
