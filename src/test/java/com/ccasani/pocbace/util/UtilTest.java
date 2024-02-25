package com.ccasani.pocbace.util;

import com.ccasani.pocbace.model.entity.UsuarioEntity;
import com.ccasani.pocbace.model.response.UsuarioResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class UtilTest {

    private UtilTest() {
    }

    public static List<UsuarioEntity> getUsuariosEntity(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<UsuarioEntity> usuarioEntities = null;
        try {
            usuarioEntities = mapper.readValue(new File(path), new TypeReference<List<UsuarioEntity>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return usuarioEntities;
    }

    public static List<UsuarioResponse> getUsuarioResponse(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<UsuarioResponse> response = null;
        try {
            response = mapper.readValue(new File(path), new TypeReference<List<UsuarioResponse>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
