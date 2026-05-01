package org.example.backend_sistema_empleo.dto;

import lombok.*;
import java.time.LocalDate;

/**
 * DTO de salida: define exactamente lo que devuelve la API.
 * Expone solo los campos necesarios del seguimiento de postulación.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoPostulacionDto {

    private Long idSeguimiento;
    private LocalDate fechaCambio;
    private String estadoAnterior;
    private String estadoNuevo;
    private String observacion;
    private Long idPostulacion;
}
