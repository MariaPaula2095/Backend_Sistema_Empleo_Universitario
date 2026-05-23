package org.example.backend_sistema_empleo.service;

import lombok.RequiredArgsConstructor;
import org.example.backend_sistema_empleo.model.Empresa;
import org.example.backend_sistema_empleo.model.Usuario;
import org.example.backend_sistema_empleo.repository.EmpresaRepository;
import org.example.backend_sistema_empleo.repository.UsuarioRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    private static final List<String> TIPOS_IMAGEN = List.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    // ─── SUBIR / ACTUALIZAR USUARIO ────────────────────────────

    @Override
    public void subirFotoUsuario(Long idUsuario, MultipartFile archivo) {
        validarImagen(archivo);
        Usuario usuario = buscarUsuario(idUsuario);
        try {
            usuario.setFotoPerfil(archivo.getBytes());
            usuario.setFotoPerfilTipo(archivo.getContentType());
            usuarioRepository.save(usuario);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
        }
    }

    // ─── SUBIR / ACTUALIZAR EMPRESA FOTO ───────────────────────

    @Override
    public void subirFotoEmpresa(Long idEmpresa, MultipartFile archivo) {
        validarImagen(archivo);
        Empresa empresa = buscarEmpresa(idEmpresa);
        try {
            empresa.setFotoPerfil(archivo.getBytes());
            empresa.setFotoPerfilTipo(archivo.getContentType());
            empresaRepository.save(empresa);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
        }
    }

    // ─── SUBIR / ACTUALIZAR EMPRESA DOCUMENTO ──────────────────

    @Override
    public void subirDocumentoEmpresa(Long idEmpresa, MultipartFile archivo) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        try {
            empresa.setDocumento(archivo.getBytes());
            empresa.setDocumentoTipo(archivo.getContentType());
            empresa.setDocumentoNombre(archivo.getOriginalFilename());
            empresaRepository.save(empresa);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
        }
    }

    // ─── OBTENER ───────────────────────────────────────────────

    @Override
    public ResponseEntity<byte[]> obtenerFotoUsuario(Long idUsuario) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario.getFotoPerfil() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "El usuario no tiene foto");
        }
        String tipo = (usuario.getFotoPerfilTipo() != null)
                ? usuario.getFotoPerfilTipo()
                : "image/jpeg";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipo))
                .body(usuario.getFotoPerfil());
    }
    @Override
    public ResponseEntity<byte[]> obtenerFotoEmpresa(Long idEmpresa) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        if (empresa.getFotoPerfil() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "La empresa no tiene foto");
        }
        String tipo = (empresa.getFotoPerfilTipo() != null)
                ? empresa.getFotoPerfilTipo()
                : "image/jpeg";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipo))
                .body(empresa.getFotoPerfil());
    }

    @Override
    public ResponseEntity<byte[]> obtenerDocumentoEmpresa(Long idEmpresa) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        if (empresa.getDocumento() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "La empresa no tiene documento");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(empresa.getDocumentoTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + empresa.getDocumentoNombre() + "\"")
                .body(empresa.getDocumento());
    }

    // ─── ELIMINAR ──────────────────────────────────────────────

    @Override
    public void eliminarFotoUsuario(Long idUsuario) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario.getFotoPerfil() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "El usuario no tiene foto para eliminar");
        }
        usuario.setFotoPerfil(null);
        usuario.setFotoPerfilTipo(null);
        usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarFotoEmpresa(Long idEmpresa) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        if (empresa.getFotoPerfil() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "La empresa no tiene foto para eliminar");
        }
        empresa.setFotoPerfil(null);
        empresa.setFotoPerfilTipo(null);
        empresaRepository.save(empresa);
    }

    @Override
    public void eliminarDocumentoEmpresa(Long idEmpresa) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        if (empresa.getDocumento() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "La empresa no tiene documento para eliminar");
        }
        empresa.setDocumento(null);
        empresa.setDocumentoTipo(null);
        empresa.setDocumentoNombre(null);
        empresaRepository.save(empresa);
    }

    // ─── HELPERS ───────────────────────────────────────────────

    private void validarImagen(MultipartFile archivo) {
        String tipo = archivo.getContentType();
        if (tipo == null || !TIPOS_IMAGEN.contains(tipo)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Solo se permiten imágenes (jpg, png, webp, gif)");
        }
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private Empresa buscarEmpresa(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Empresa no encontrada"));
    }
}