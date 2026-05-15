package org.example.backend_sistema_empleo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
@Entity
@Table(name = "empresa_pendiente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmpresaPendiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresaPendiente;

    private String nombre;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    // PENDIENTE - APROBADA - RECHAZADA
    private String estado;

    @Column(length = 500)
    private String mensaje;

    private Integer rechazos = 0;

    private Boolean activo = true;
}