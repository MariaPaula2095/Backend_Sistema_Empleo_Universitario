package org.example.backend_sistema_empleo.dto;

import lombok.*;

/**
 * DTO de salida: define exactamente lo que devuelve la API.
 * Expone solo los campos necesarios del perfil profesional.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilDto {

    private Long idPerfil;
    private String carrera;
    private String universidad;
    private String semestre;
    private String habilidades;
    private String experiencia;
    private String cvUrl;
    private String disponibilidad;
    private Long idUsuario;
}