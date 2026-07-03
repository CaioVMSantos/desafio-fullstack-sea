package com.sea.desafiobackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDTO {

    @NotBlank(message = "O CEP é obrigatório!")
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório!")
    private String logradouro;

    @NotBlank(message = "O bairro é obrigatório!")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória!")
    private String cidade;

    @NotBlank(message = "A uf é obrigatória!")
    private String uf;

    private String complemento;


}
