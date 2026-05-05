package org.example.backend_sistema_empleo.dto;

import lombok.*;
import org.example.backend_sistema_empleo.model.EstadoPostulacion;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulacionDto {

    private Long idPostulacion;
    private LocalDate fechaPostulacion;
    private EstadoPostulacion estado;
    private Long idUsuario;
    private Long idOferta;
}