package org.example.backend_sistema_empleo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {
    void subirFotoUsuario(Long idUsuario, MultipartFile archivo);
    void subirFotoEmpresa(Long idEmpresa, MultipartFile archivo);
    void subirDocumentoEmpresa(Long idEmpresa, MultipartFile archivo);

    ResponseEntity<byte[]> obtenerFotoUsuario(Long idUsuario);
    ResponseEntity<byte[]> obtenerFotoEmpresa(Long idEmpresa);
    ResponseEntity<byte[]> obtenerDocumentoEmpresa(Long idEmpresa);

    void eliminarFotoUsuario(Long idUsuario);
    void eliminarFotoEmpresa(Long idEmpresa);
    void eliminarDocumentoEmpresa(Long idEmpresa);
}