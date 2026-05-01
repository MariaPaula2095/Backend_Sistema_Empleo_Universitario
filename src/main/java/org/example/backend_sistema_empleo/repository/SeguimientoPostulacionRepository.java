package org.example.backend_sistema_empleo.repository;

import org.example.backend_sistema_empleo.model.SeguimientoPostulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeguimientoPostulacionRepository extends JpaRepository<SeguimientoPostulacion, Long> {

    @Query(value = """
        SELECT * 
        FROM seguimiento_postulacion 
        WHERE id_postulacion = :idPostulacion
        ORDER BY fecha_cambio ASC
        """, nativeQuery = true)
    List<SeguimientoPostulacion> obtenerHistorialPorPostulacion(@Param("idPostulacion") Long idPostulacion);

}