package com.sea.desafiobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponseDTO {

    private String mensagem;
    private String token;
    private String tipo;

}