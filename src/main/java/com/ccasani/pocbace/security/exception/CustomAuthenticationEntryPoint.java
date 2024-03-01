package com.ccasani.pocbace.security.exception;

import com.ccasani.pocbace.common.MessageComponent;
import com.ccasani.pocbace.model.ErrorTypeEnum;
import com.ccasani.pocbace.model.MessageKey;
import com.ccasani.pocbace.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final MessageComponent messageComponent;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorResponse re = ErrorResponse.builder().code(ErrorTypeEnum.CODE_AUTHENTICATION.getCode()).message(this.messageComponent.getMessagesKey(MessageKey.REQUEST_AUTENTICACION_ERROR)).build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }

}
