package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.OfertaLaboralDto;
import java.util.List;

public interface OfertaLaboralService {

    List<OfertaLaboralDto> listar();

    OfertaLaboralDto guardar(OfertaLaboralDto dto);

    OfertaLaboralDto actualizar(Long id, OfertaLaboralDto dto);

    void eliminar(Long id);

    List<OfertaLaboralDto> buscarPorArea(String area);

    List<OfertaLaboralDto> buscarPorCargo(String cargo);

    List<OfertaLaboralDto> listarOfertasActivas();

    // 🏢 NUEVO: ofertas por empresa (IMPORTANTE PARA LOGIN EMPRESA)
    List<OfertaLaboralDto> listarPorEmpresa(Long idEmpresa);
}