package com.ccasani.pocbace.security.service;

import com.ccasani.pocbace.model.CustomException;
import com.ccasani.pocbace.model.entity.UsuarioEntity;
import com.ccasani.pocbace.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    public Optional<UsuarioEntity> getByNombreUsuario(String nombreUsuario){
        return Optional.ofNullable(usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new CustomException("9999", "Nombre usuario no existe.", HttpStatus.BAD_REQUEST)));
    }

}
