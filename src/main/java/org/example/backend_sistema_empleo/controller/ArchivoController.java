package org.example.backend_sistema_empleo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.example.backend_sistema_empleo.service.ArchivoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/archivos")
@RequiredArgsConstructor
public class ArchivoController {

    private final ArchivoService archivoService;

    // ─── SUBIR ─────────────────────────────────────────────────

    @Operation(summary = "Subir foto de usuario")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @PostMapping(value = "/foto/usuario/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> subirFotoUsuario(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirFotoUsuario(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Foto subida correctamente"));
    }

    @Operation(summary = "Subir foto de empresa")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @PostMapping(value = "/foto/empresa/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> subirFotoEmpresa(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirFotoEmpresa(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Foto subida correctamente"));
    }

    @Operation(summary = "Subir documento de empresa")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @PostMapping(value = "/documento/empresa/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> subirDocumentoEmpresa(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirDocumentoEmpresa(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Documento subido correctamente"));
    }

    // ─── OBTENER ───────────────────────────────────────────────

    @GetMapping("/foto/usuario/{id}")
    public ResponseEntity<byte[]> obtenerFotoUsuario(@PathVariable Long id) {
        return archivoService.obtenerFotoUsuario(id);
    }

    @GetMapping("/foto/empresa/{id}")
    public ResponseEntity<byte[]> obtenerFotoEmpresa(@PathVariable Long id) {
        return archivoService.obtenerFotoEmpresa(id);
    }

    @GetMapping("/documento/empresa/{id}")
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<byte[]> obtenerDocumentoEmpresa(@PathVariable Long id) {
        return archivoService.obtenerDocumentoEmpresa(id);
    }

    // ─── ACTUALIZAR ────────────────────────────────────────────

    @Operation(summary = "Actualizar foto de usuario")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @PutMapping(value = "/foto/usuario/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> actualizarFotoUsuario(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirFotoUsuario(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Foto actualizada correctamente"));
    }

    @Operation(summary = "Actualizar foto de empresa")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @PutMapping(value = "/foto/empresa/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> actualizarFotoEmpresa(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirFotoEmpresa(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Foto actualizada correctamente"));
    }

    @Operation(summary = "Actualizar documento de empresa")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @PutMapping(value = "/documento/empresa/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> actualizarDocumentoEmpresa(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirDocumentoEmpresa(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Documento actualizado correctamente"));
    }

    // ─── ELIMINAR ──────────────────────────────────────────────

    @DeleteMapping("/foto/usuario/{id}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarFotoUsuario(@PathVariable Long id) {
        archivoService.eliminarFotoUsuario(id);
        return ResponseEntity.ok(Map.of("mensaje", "Foto eliminada correctamente"));
    }

    @DeleteMapping("/foto/empresa/{id}")
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarFotoEmpresa(@PathVariable Long id) {
        archivoService.eliminarFotoEmpresa(id);
        return ResponseEntity.ok(Map.of("mensaje", "Foto eliminada correctamente"));
    }

    @DeleteMapping("/documento/empresa/{id}")
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarDocumentoEmpresa(@PathVariable Long id) {
        archivoService.eliminarDocumentoEmpresa(id);
        return ResponseEntity.ok(Map.of("mensaje", "Documento eliminado correctamente"));
    }

    @Operation(summary = "Subir documento de empresa pendiente")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    @PostMapping(value = "/documento/empresa-pendiente/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> subirDocumentoEmpresaPendiente(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirDocumentoEmpresaPendiente(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Documento subido correctamente"));
    }

    @GetMapping("/documento/empresa-pendiente/{id}")
    public ResponseEntity<byte[]> obtenerDocumentoEmpresaPendiente(@PathVariable Long id) {
        return archivoService.obtenerDocumentoEmpresaPendiente(id);
    }

    @PutMapping(value = "/documento/empresa-pendiente/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Actualizar documento de empresa pendiente")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    public ResponseEntity<Map<String, String>> actualizarDocumentoEmpresaPendiente(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        archivoService.subirDocumentoEmpresaPendiente(id, archivo);
        return ResponseEntity.ok(Map.of("mensaje", "Documento actualizado correctamente"));
    }

    @DeleteMapping("/documento/empresa-pendiente/{id}")
    public ResponseEntity<Map<String, String>> eliminarDocumentoEmpresaPendiente(@PathVariable Long id) {
        archivoService.eliminarDocumentoEmpresaPendiente(id);
        return ResponseEntity.ok(Map.of("mensaje", "Documento eliminado correctamente"));
    }
}