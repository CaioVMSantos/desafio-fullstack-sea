package com.sea.desafiobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // Avisa ao Spring que esta classe vai escutar os erros de todos os Controllers
public class GlobalExceptionHandler {

    // 1. TRATAMENTO DE REGRAS DE NEGÓCIO (Service)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {

        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST.value(), // Código 400
                ex.getMessage(), // Mensagem da Service
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 2. TRATAMENTO DE VALIDAÇÕES DO DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // Pega a lista de todos os campos que falharam na validação e formata a string
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