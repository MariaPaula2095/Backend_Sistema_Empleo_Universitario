package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.UsuarioDto;
import org.example.backend_sistema_empleo.model.Usuario;
import org.example.backend_sistema_empleo.model.Rol;
import org.example.backend_sistema_empleo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UsuarioDto convertirADto(Usuario u) {
        return new UsuarioDto(
                u.getIdUsuario(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getTelefono(),
                u.getTipoUsuario(), // 🔥 ahora es enum
                u.getFechaRegistro(),
                u.getEstado(),
                null
        );
    }

    private Usuario convertirAEntity(UsuarioDto dto) {
        Usuario u = new Usuario();
        u.setIdUsuario(dto.getIdUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setEmail(dto.getEmail());
        u.setTelefono(dto.getTelefono());

        if (dto.getTipoUsuario() != null) {
            u.setTipoUsuario(dto.getTipoUsuario());
        } else {
            u.setTipoUsuario(Rol.ESTUDIANTE);
        }

        u.setFechaRegistro(dto.getFechaRegistro());
        u.setEstado(dto.getEstado());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return u;
    }

    @Override
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto guardar(UsuarioDto dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = convertirAEntity(dto);
        return convertirADto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDto actualizar(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getTelefono() != null) usuario.setTelefono(dto.getTelefono());
        if (dto.getTipoUsuario() != null) usuario.setTipoUsuario(dto.getTipoUsuario());
        if (dto.getEstado() != null) usuario.setEstado(dto.getEstado());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return convertirADto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDto login(String email, String password) {

        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return convertirADto(u);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDto buscarPorEmail(String email) {
        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return convertirADto(u);
    }
    @Override
    public String recuperarPassword(String email) {

        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String nuevaPass = java.util.UUID.randomUUID().toString().substring(0, 8);

        u.setPassword(passwordEncoder.encode(nuevaPass));

        usuarioRepository.save(u);

        return nuevaPass;
    }
}