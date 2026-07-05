package com.sea.desafiobackend.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotBlank(message = "A palavra-passe é obrigatória")
    private String senha;

}