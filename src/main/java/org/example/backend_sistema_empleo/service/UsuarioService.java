package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.UsuarioDto;
import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> listar();

    UsuarioDto guardar(UsuarioDto usuarioDto);

    UsuarioDto actualizar(Long id, UsuarioDto usuarioDto);

    UsuarioDto login(String email, String password);

    void eliminar(Long id);

    UsuarioDto buscarPorEmail(String email);

    String recuperarPassword(String email);
}