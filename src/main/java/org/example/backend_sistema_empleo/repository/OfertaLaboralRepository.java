package org.example.backend_sistema_empleo.repository;


import org.example.backend_sistema_empleo.model.OfertaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OfertaLaboralRepository extends JpaRepository<OfertaLaboral, Long> {

    @Query(value = "SELECT * FROM oferta_laboral WHERE area = :area", nativeQuery = true)
    List<OfertaLaboral> findByArea(@Param("area") String area);

    @Query(value = "SELECT * FROM oferta_laboral WHERE titulo LIKE %:cargo%", nativeQuery = true)
    List<OfertaLaboral> findByCargo(@Param("cargo") String cargo);

    @Query(value = "SELECT * FROM oferta_laboral WHERE estado = true", nativeQuery = true)
    List<OfertaLaboral> findAllActive();
}