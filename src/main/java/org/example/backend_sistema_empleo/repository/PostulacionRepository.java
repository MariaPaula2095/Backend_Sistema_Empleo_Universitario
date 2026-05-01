package org.example.backend_sistema_empleo.repository;

import org.example.backend_sistema_empleo.model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    @Query(value = "SELECT * FROM postulacion WHERE id_usuario = :idUsuario", nativeQuery = true)
    List<Postulacion> findByUsuario(@Param("idUsuario") Long idUsuario);

    @Query(value = "SELECT * FROM postulacion WHERE id_oferta = :idOferta", nativeQuery = true)
    List<Postulacion> findCandidatosByOferta(@Param("idOferta") Long idOferta);
}