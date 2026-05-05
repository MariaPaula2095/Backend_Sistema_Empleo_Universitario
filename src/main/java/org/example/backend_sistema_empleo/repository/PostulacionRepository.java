package org.example.backend_sistema_empleo.repository;

import org.example.backend_sistema_empleo.model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    // 🔐 VALIDAR DUPLICADO (SQL NATIVO - lo puedes mantener)
    @Query(value = """
        SELECT * FROM postulacion 
        WHERE id_usuario = :idUsuario 
        AND id_oferta = :idOferta
    """, nativeQuery = true)
    Optional<Postulacion> existePostulacion(@Param("idUsuario") Long idUsuario,
                                            @Param("idOferta") Long idOferta);

    // 📊 POSTULACIONES POR USUARIO (SQL NATIVO)
    @Query(value = "SELECT * FROM postulacion WHERE id_usuario = :idUsuario", nativeQuery = true)
    List<Postulacion> findByUsuario(@Param("idUsuario") Long idUsuario);

    List<Postulacion> findByUsuarioIdUsuario(Long idUsuario);

    List<Postulacion> findByOfertaLaboralIdOferta(Long idOferta);
}