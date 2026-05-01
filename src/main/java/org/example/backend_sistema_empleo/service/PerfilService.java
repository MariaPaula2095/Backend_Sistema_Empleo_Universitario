package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.PerfilDto;

import java.util.List;

public interface PerfilService {

    List<PerfilDto> listar();

    PerfilDto guardar(PerfilDto dto);

    PerfilDto actualizar(Long id, PerfilDto dto);

    void eliminar(Long id);

    List<PerfilDto> buscarPorCarrera(String carrera);

    List<PerfilDto> buscarPorHabilidad(String habilidad);
}