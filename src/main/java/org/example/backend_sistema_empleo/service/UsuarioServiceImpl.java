package org.example.backend_sistema_empleo.service;

import org.example.backend_sistema_empleo.dto.UsuarioDto;
import org.example.backend_sistema_empleo.model.Usuario;
import org.example.backend_sistema_empleo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private UsuarioDto convertirADto(Usuario u) {
        return new UsuarioDto(
                u.getIdUsuario(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getTelefono(),
                u.getTipoUsuario(),
                u.getFechaRegistro(),
                u.getEstado(),
                u.getPassword()
        );
    }

    private Usuario convertirAEntity(UsuarioDto dto) {
        Usuario u = new Usuario();
        u.setIdUsuario(dto.getIdUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setEmail(dto.getEmail());
        u.setTelefono(dto.getTelefono());
        u.setTipoUsuario(dto.getTipoUsuario());
        u.setFechaRegistro(dto.getFechaRegistro());
        u.setEstado(dto.getEstado());
        u.setPassword(dto.getPassword());
        return u;
    }

    @Override
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll()
                .stream().map(this::convertirADto).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto guardar(UsuarioDto dto) {
        return convertirADto(usuarioRepository.save(convertirAEntity(dto)));
    }

    @Override
    public UsuarioDto actualizar(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualización parcial (solo si no es null)
        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }

        if (dto.getApellido() != null) {
            usuario.setApellido(dto.getApellido());
        }

        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }

        if (dto.getTelefono() != null) {
            usuario.setTelefono(dto.getTelefono());
        }

        if (dto.getTipoUsuario() != null) {
            usuario.setTipoUsuario(dto.getTipoUsuario());
        }

        if (dto.getEstado() != null) {
            usuario.setEstado(dto.getEstado());
        }

        // 🔥 CLAVE: solo actualizar password si viene
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(dto.getPassword());
        }

        // ⚠️ fechaRegistro normalmente NO se debería modificar, pero si quieres:
        if (dto.getFechaRegistro() != null) {
            usuario.setFechaRegistro(dto.getFechaRegistro());
        }

        return convertirADto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDto login(String email, String password) {
        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!u.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return convertirADto(u);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Consulta nativa
    @Override
    public UsuarioDto buscarPorEmail(String email) {
        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return convertirADto(u);
    }
}
