package com.ccasani.pocbace.service;

import com.ccasani.pocbace.common.MessageComponent;
import com.ccasani.pocbace.model.entity.UsuarioEntity;
import com.ccasani.pocbace.model.request.UsuarioRequest;
import com.ccasani.pocbace.model.response.UsuarioResponse;
import com.ccasani.pocbace.repository.UsuarioRepository;
import com.ccasani.pocbace.util.UtilTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {
    private static final String PATH_JOSN_RESPONSE = "src/test/resources/usuarios";
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private MessageComponent messageComponent;
    ;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @Disabled
    void exportar() {
    }

    @Test
    void saveUsuario() {
        UsuarioService usuarioService = new UsuarioServiceImpl(usuarioRepository, messageComponent);
        UsuarioRequest usuarioRequest = UsuarioRequest.builder().build();
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        given(this.usuarioRepository.save(any())).willReturn(usuarioEntity);

        usuarioService.saveUsuario(usuarioRequest);

        verify(usuarioRepository).save(usuarioEntity);

    }

    @Test
    void existsByCorreoTest() {

        given(this.usuarioRepository.existsByCorreo(any())).willReturn(true);

        boolean response = usuarioService.existsByCorreo("mau@gmail.com");
        Assertions.assertTrue(response);


    }


    @Test
    void listarUsuarios() {
        given(this.usuarioRepository.findAll()).
                willReturn(UtilTest.getUsuariosEntity(PATH_JOSN_RESPONSE.concat("/listar_usuarios_ok.json")));
        List<UsuarioResponse> usuarioRequestList = this.usuarioService.listarUsuarios();
        Assertions.assertNotNull(usuarioRequestList);
    }
}