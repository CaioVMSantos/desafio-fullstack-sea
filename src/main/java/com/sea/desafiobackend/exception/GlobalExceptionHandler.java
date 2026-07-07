package com.sea.desafiobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {

        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errosDeValidacao = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Falha na validação dos dados enviados.",
                errosDeValidacao
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}