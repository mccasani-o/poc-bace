package com.ccasani.pocbace.model;

import com.ccasani.pocbace.model.entity.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEvent {
    private UsuarioEntity usuarioEntity;
}
