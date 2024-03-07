package com.ccasani.pocbace.service;

import com.ccasani.pocbace.model.request.UsuarioRequest;
import com.ccasani.pocbace.model.response.UsuarioResponse;

import java.io.IOException;
import java.util.List;

public interface UsuarioService {

    byte[] exportar() throws IOException;

    byte[] exportData() throws IOException;
    byte[] exportData2() throws IOException;

    void saveUsuario(UsuarioRequest usuarioRequest);

    List<UsuarioResponse> listarUsuarios();

    boolean existsByCorreo(String correo);
}
