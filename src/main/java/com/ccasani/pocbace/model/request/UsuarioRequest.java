package com.ccasani.pocbace.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank
    private String nombreUsuario;
    @Email(message = "{request.error.message.correo}")
    private String correo;
    @NotBlank(message = "{request.error.message.password}")
    @Size(min = 6, max = 20, message
            = "Password debe tener entre 6 y 20 caracteres")
    private String password;
}
