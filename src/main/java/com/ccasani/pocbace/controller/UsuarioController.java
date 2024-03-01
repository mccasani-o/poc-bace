package com.ccasani.pocbace.controller;

import com.ccasani.pocbace.model.request.UsuarioRequest;
import com.ccasani.pocbace.model.response.UsuarioResponse;
import com.ccasani.pocbace.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public void saveUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        this.usuarioService.saveUsuario(usuarioRequest);
    }

    @GetMapping
    public List<UsuarioResponse> obtenerUsuarios() {
        return this.usuarioService.listarUsuarios();
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportDataExel(HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        response.setHeader("Content-Disposition", "attachment; filename=sample" + UUID.randomUUID().toString() + ".xlsx");
        return ResponseEntity.ok().headers(headers).body(this.usuarioService.exportData());
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        response.setHeader("Content-Disposition", "attachment; filename=sample2" + UUID.randomUUID().toString() + ".xlsx");
        return ResponseEntity.ok().headers(headers).body(this.usuarioService.exportar());
    }
}
