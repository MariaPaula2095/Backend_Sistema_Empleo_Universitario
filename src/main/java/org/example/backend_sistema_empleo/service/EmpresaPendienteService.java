package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.model.EmpresaPendiente;

import java.util.List;

public interface EmpresaPendienteService {

    EmpresaPendiente crear(EmpresaPendiente emp);

    List<EmpresaPendiente> listar();

    EmpresaPendiente aprobar(Long id, String mensaje);

    EmpresaPendiente rechazar(Long id, String mensaje);
}