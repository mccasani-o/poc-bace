package com.ccasani.pocbace.controller;

import com.ccasani.pocbace.common.MessageComponent;
import com.ccasani.pocbace.model.request.UsuarioRequest;
import com.ccasani.pocbace.model.response.UsuarioResponse;
import com.ccasani.pocbace.repository.UsuarioRepository;
import com.ccasani.pocbace.service.UsuarioService;
import com.ccasani.pocbace.util.UtilTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {
    private static final String PATH_JOSN_RESPONSE = "src/test/resources/controller";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private MessageComponent messageComponent;

    private List<UsuarioResponse> responseList;
    private UsuarioRequest usuarioRequest;

    @BeforeEach
    public void setUp() {
        this.responseList = UtilTest.getUsuarioResponse(PATH_JOSN_RESPONSE.concat("/listar_usuarios_ok.json"));
        this.usuarioRequest = UsuarioRequest.builder().correo("mau@gmail.com").password("1234abc").build();
    }

    @Test
    void saveUsuarioTest() throws Exception {


        this.mockMvc.perform(post("/usuarios")
                        .content(this.mapper.writeValueAsString(this.usuarioRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void saveUsuarioBadRequest() throws Exception {

        UsuarioRequest usuarioRequest=    UsuarioRequest.builder().correo("mau@gmail.com").password("1234").build();
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Is.is("111")))
                .andExpect(jsonPath("$.message", Is.is("Password debe tener entre 6 y 20 caracteres")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void obtenerUsuariosTest() throws Exception {
        given(this.usuarioService.listarUsuarios()).willReturn(responseList);
        this.mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}