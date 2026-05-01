package org.example.backend_sistema_empleo.dto;

import lombok.*;
import java.time.LocalDate;

/**
 * DTO de salida: define exactamente lo que devuelve la API.
 * Expone solo los campos necesarios de la oferta laboral.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaLaboralDto {

    private Long idOferta;
    private String titulo;
    private String descripcion;
    private String area;
    private Double salario;
    private String modalidad;
    private LocalDate fechaPublicacion;
    private LocalDate fechaCierre;
    private Boolean estado;
    private Long idEmpresa;
}
