package com.ccasani.pocbace.security.model;

import com.ccasani.pocbace.model.entity.UsuarioEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UsuarioPrincipal implements UserDetails {
    private final Long id;
    private final String nombreUsuario;
    private final String correo;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(Long id, String nombreUsuario, String correo, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.password = password;
        this.authorities = authorities;
    }
    public static UsuarioPrincipal build(UsuarioEntity usuario){

        return new UsuarioPrincipal(usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getCorreo(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority((usuario.getRole().name()))));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
