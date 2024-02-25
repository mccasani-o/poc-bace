package com.ccasani.pocbace.service;

import com.ccasani.pocbace.model.ReporteEnum;
import com.ccasani.pocbace.model.request.UsuarioRequest;
import com.ccasani.pocbace.model.response.UsuarioResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface UsuarioService {

    byte[] exportar () throws IOException;
    public byte[] exportData() throws IOException;
    
    void saveUsuario(UsuarioRequest usuarioRequest);

    List<UsuarioResponse> listarUsuarios();

    boolean existsByCorreo(String correo);
}
