package org.example.backend_sistema_empleo.repository;

import org.example.backend_sistema_empleo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuario WHERE email = :email", nativeQuery = true)
    Optional<Usuario> findByEmail(@Param("email") String email);

}