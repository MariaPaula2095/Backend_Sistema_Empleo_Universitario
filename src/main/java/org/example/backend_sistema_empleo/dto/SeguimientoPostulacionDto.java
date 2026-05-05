package org.example.backend_sistema_empleo.dto;

import lombok.*;
import java.time.LocalDate;


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
