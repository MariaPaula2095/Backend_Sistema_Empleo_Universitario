package org.example.backend_sistema_empleo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDto {

    private Long idEmpresa;
    private String nombre;
    private String sector;
    private String descripcion;
    private String email;
    private String telefono;
    private String ciudad;
    private String rol;

    @JsonProperty(access = WRITE_ONLY)
    private String password;
}