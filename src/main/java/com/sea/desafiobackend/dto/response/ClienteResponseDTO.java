package com.sea.desafiobackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private EnderecoResponseDTO endereco;
    private List<TelefoneResponseDTO> telefones;
    private List<EmailResponseDTO> emails;

}
