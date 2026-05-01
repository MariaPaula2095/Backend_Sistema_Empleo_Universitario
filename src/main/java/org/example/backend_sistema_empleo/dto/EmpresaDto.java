package org.example.backend_sistema_empleo.dto;

import lombok.*;

/**
 * DTO de salida: define exactamente lo que devuelve la API.
 * Expone solo los campos necesarios de la empresa.
 */

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