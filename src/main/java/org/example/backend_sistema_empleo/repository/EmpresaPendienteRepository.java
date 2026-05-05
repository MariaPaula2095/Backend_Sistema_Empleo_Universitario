package org.example.backend_sistema_empleo.repository;

import org.example.backend_sistema_empleo.model.EmpresaPendiente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaPendienteRepository extends JpaRepository<EmpresaPendiente, Long> {
}