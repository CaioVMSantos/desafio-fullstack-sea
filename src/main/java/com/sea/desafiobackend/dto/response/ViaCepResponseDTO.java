package com.sea.desafiobackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaCepResponseDTO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // ViaCep usa "localidade" como cidade
    private String uf;
    private Boolean erro;

}
