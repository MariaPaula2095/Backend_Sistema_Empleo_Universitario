package org.example.backend_sistema_empleo.service;


import org.example.backend_sistema_empleo.dto.SeguimientoPostulacionDto;
import java.util.List;

public interface SeguimientoPostulacionService {

    List<SeguimientoPostulacionDto> listar();
    SeguimientoPostulacionDto guardar(SeguimientoPostulacionDto seguimientoDto);
    SeguimientoPostulacionDto actualizar(Long id, SeguimientoPostulacionDto seguimientoDto);
    void eliminar(Long id);

    // Consulta nativa
    List<SeguimientoPostulacionDto> historialPorPostulacion(Long idPostulacion);
}
