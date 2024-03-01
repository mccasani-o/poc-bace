package com.ccasani.pocbace.security.service;

import com.ccasani.pocbace.model.entity.UsuarioEntity;
import com.ccasani.pocbace.repository.UsuarioRepository;
import com.ccasani.pocbace.security.jwt.JwtService;
import com.ccasani.pocbace.security.model.AuthResponse;
import com.ccasani.pocbace.security.model.LoginRequest;
import com.ccasani.pocbace.security.model.RegisterRequest;
import com.ccasani.pocbace.security.model.RolNombre;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Optional<UsuarioEntity> user = Optional.of(userRepository.findByNombreUsuario(request.getUsername()).orElseThrow());
        UserDetails userDetails = user.get();
        String token = jwtService.getToken(userDetails);

        return AuthResponse.builder()
                .username(user.get().getUsername())
                .correo(user.get().getCorreo())
                .rol(String.valueOf(user.get().getRole()))
                .token(token)
                .build();

    }

    public void register(RegisterRequest request) {
        UsuarioEntity user = UsuarioEntity.builder()
                .nombreUsuario(request.getUsername())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RolNombre.USER)
                .build();

        userRepository.save(user);

    }
}
