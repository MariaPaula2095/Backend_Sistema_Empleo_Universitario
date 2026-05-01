package org.example.backend_sistema_empleo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "oferta_laboral")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OfertaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOferta;

    private String titulo;
    private String descripcion;
    private String area;
    private Double salario;
    private String modalidad; // PRESENCIAL, REMOTO, HIBRIDO
    private LocalDate fechaPublicacion;
    private LocalDate fechaCierre;
    private Boolean estado; // true = activa, false = inactiva

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}