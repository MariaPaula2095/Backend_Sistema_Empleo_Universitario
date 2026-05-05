package org.example.backend_sistema_empleo.model;

import jakarta.persistence.*;
import lombok.*;

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

    private String email;

    private String estado; // PENDIENTE, APROBADA, RECHAZADA

    private String mensaje;
}