package org.example.backend_sistema_empleo.dto;

import lombok.*;
import java.time.LocalDate;

/**
 * DTO de salida: define exactamente lo que devuelve la API.
 * Expone solo los campos necesarios de la postulación.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulacionDto {

    private Long idPostulacion;
    private LocalDate fechaPostulacion;
    private String estado;
    private Long idUsuario;
    private Long idOferta;
}