package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.EmpresaDto;
import org.example.backend_sistema_empleo.model.Empresa;

import java.util.List;

public interface EmpresaService {

    List<EmpresaDto> listar();

    EmpresaDto guardar(EmpresaDto dto);

    EmpresaDto actualizar(Long id, EmpresaDto dto);

    void eliminar(Long id);

    List<EmpresaDto> listarEmpresasConMasOfertas();

    // 🔐 NUEVO: LOGIN EMPRESA
    Empresa login(String email, String password);
}