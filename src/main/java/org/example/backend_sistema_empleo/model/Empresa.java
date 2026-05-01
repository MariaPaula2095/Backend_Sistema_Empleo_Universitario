package org.example.backend_sistema_empleo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresa;

    private String nombre;
    private String sector;
    private String descripcion;
    private String email;
    private String telefono;
    private String ciudad;
}
