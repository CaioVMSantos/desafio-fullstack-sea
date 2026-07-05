package com.sea.desafiobackend.dto.request;

import com.sea.desafiobackend.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistroRequestDTO {

    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @NotNull(message = "O perfil (ROLE) é obrigatório")
    private Perfil perfil;

}
