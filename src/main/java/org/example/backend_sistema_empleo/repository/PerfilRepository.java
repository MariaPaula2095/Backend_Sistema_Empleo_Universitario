package org.example.backend_sistema_empleo.repository;
import org.example.backend_sistema_empleo.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query (value = "SELECT * FROM perfil WHERE LOWER(carrera) LIKE LOWER(CONCAT('%', :carrera,'%'))",nativeQuery = true)
    List<Perfil> buscarPorCarrera(@Param("carrera") String carrera);

    @Query (value = "SELECT * FROM perfil WHERE LOWER(habilidades) LIKE LOWER(CONCAT('%', :habilidad,'%'))",nativeQuery = true)
    List<Perfil> buscarPorHabilidad(@Param("habilidad")String habilidad);


}
