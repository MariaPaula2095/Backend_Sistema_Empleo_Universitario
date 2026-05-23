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

    @Column(unique = true, nullable = false)
    private String email;

    private String telefono;

    private String ciudad;

    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @PrePersist
    public void prePersist() {

        if (this.rol == null) {
            this.rol = Rol.EMPRESA;
        }
    }

    @Column(columnDefinition = "bytea")
    private byte[] fotoPerfil;

    private String fotoPerfilTipo;

    @Column(columnDefinition = "bytea")
    private byte[] documento;

    private String documentoTipo; // ej: "application/pdf"
    private String documentoNombre; // nombre original del archivo
}