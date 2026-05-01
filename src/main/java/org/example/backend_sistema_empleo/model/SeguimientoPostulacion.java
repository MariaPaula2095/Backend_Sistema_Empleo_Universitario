package org.example.backend_sistema_empleo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "seguimiento_postulacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeguimientoPostulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguimiento;

    private LocalDate fechaCambio;
    private String estadoAnterior;
    private String estadoNuevo;
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "id_postulacion")
    private Postulacion postulacion;
}