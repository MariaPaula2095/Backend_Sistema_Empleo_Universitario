package org.example.backend_sistema_empleo.dto;

import lombok.*;


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
}