package org.example.backend_sistema_empleo.model;
//
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)*(gmail\\.com|edu\\.co)$",
            message = "El correo debe ser @gmail.com o un dominio educativo .edu.co"
    )
    @Column(unique = true)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 10, message = "El teléfono no puede tener más de 10 caracteres")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol tipoUsuario;

    private LocalDate fechaRegistro;

    private Boolean estado;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Perfil perfil;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDate.now();
        this.estado = true;
        if (this.tipoUsuario == null) {
            this.tipoUsuario = Rol.ESTUDIANTE;
        }
    }
}