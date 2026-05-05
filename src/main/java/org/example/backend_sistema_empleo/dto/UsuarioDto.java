package org.example.backend_sistema_empleo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;
import org.example.backend_sistema_empleo.model.Rol;

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

    private Rol tipoUsuario;

    private LocalDate fechaRegistro;
    private Boolean estado;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}