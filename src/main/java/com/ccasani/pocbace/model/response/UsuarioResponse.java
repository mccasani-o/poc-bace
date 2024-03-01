package com.ccasani.pocbace.model.response;

import com.ccasani.pocbace.security.model.RolNombre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nombreUsuario;
    private String correo;
    private String password;
    private RolNombre role;
}
