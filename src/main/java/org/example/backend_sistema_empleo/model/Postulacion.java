package org.example.backend_sistema_empleo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "postulacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPostulacion;

    private LocalDate fechaPostulacion;
    private String estado; // PENDIENTE, EN_REVISION, ACEPTADA, RECHAZADA

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_oferta")
    private OfertaLaboral ofertaLaboral;
}
