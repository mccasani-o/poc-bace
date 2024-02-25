package com.ccasani.pocbace.model.response;

import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private Objects data;
    private String code;
    private String message;
}
