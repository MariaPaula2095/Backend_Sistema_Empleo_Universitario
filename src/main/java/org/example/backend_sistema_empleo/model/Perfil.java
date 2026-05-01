package org.example.backend_sistema_empleo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerfil;

    private String carrera;
    private String universidad;
    private String semestre;
    private String habilidades;
    private String experiencia;
    private String cvUrl;
    private String disponibilidad; // INMEDIATA, POR_DEFINIR

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}