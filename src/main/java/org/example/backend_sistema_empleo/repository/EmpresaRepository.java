package org.example.backend_sistema_empleo.repository;

import jakarta.transaction.Transactional;
import org.example.backend_sistema_empleo.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query(value = "SELECT * FROM empresa WHERE email = :email", nativeQuery = true)
    Optional<Empresa> findByEmail(@Param("email") String email);

    @Query(value = """
        SELECT e.* 
        FROM empresa e
        LEFT JOIN oferta_laboral o ON e.id_empresa = o.id_empresa
        GROUP BY e.id_empresa
        ORDER BY COUNT(o.id) DESC
    """, nativeQuery = true)
    List<Empresa> listarEmpresasConMasOfertas();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM empresa WHERE id_empresa = :id", nativeQuery = true)
    void eliminarEmpresa(@Param("id") Long id);
}