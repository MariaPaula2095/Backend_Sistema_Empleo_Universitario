package org.example.backend_sistema_empleo.dto;

import lombok.*;
import java.time.LocalDate;

/**
 * DTO de salida: define exactamente lo que devuelve la API.
 * Expone solo los campos necesarios, omite datos sensibles.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String tipoUsuario;
    private LocalDate fechaRegistro;
    private Boolean estado;
}