package com.ccasani.pocbace.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;

    public CustomException( String code,HttpStatus httpStatus) {

        this.code = code;
        this.httpStatus = httpStatus;
    }
    public CustomException( String code,String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
