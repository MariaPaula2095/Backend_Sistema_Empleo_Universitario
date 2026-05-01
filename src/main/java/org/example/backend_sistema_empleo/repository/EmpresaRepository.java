package org.example.backend_sistema_empleo.repository;

import org.example.backend_sistema_empleo.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query(value = """
        SELECT e.* 
        FROM empresa e
        JOIN oferta_laboral o ON e.id_empresa = o.empresa_id
        GROUP BY e.id_empresa
        ORDER BY COUNT(o.id_oferta) DESC
        """, nativeQuery = true)
    List<Empresa> listarEmpresasConMasOfertas();

}