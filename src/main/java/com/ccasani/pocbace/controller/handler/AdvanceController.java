package com.ccasani.pocbace.controller.handler;

import com.ccasani.pocbace.common.MessageComponent;
import com.ccasani.pocbace.model.CustomException;
import com.ccasani.pocbace.model.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RequiredArgsConstructor
@ControllerAdvice
public class AdvanceController {

    private final MessageComponent messageComponent;


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFoundException(
            CustomException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();

        if (StringUtils.isBlank(ex.getHttpStatus().toString())) {
            errorResponse.setCode("999");
            errorResponse.setMessage("Estimado cliente comunicarse con el soporte");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {


        ErrorResponse errorResponse = ErrorResponse.builder().code("111").message(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleException_(
            Exception ex) {

        ErrorResponse errorResponse = ErrorResponse.builder().code("111").message(ex.getMessage()).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
