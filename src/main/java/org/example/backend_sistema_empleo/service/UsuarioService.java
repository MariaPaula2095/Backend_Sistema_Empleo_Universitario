package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.UsuarioDto;
import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> listar();
    UsuarioDto guardar(UsuarioDto usuarioDto);
    UsuarioDto actualizar(Long id, UsuarioDto usuarioDto);
    void eliminar(Long id);

    // Consulta nativa
    UsuarioDto buscarPorEmail(String email);
}
