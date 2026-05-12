package org.example.backend_sistema_empleo.repository;

import org.example.backend_sistema_empleo.model.EmpresaPendiente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaPendienteRepository extends JpaRepository<EmpresaPendiente, Long> {

    Optional<EmpresaPendiente> findByEmail(String email);

}