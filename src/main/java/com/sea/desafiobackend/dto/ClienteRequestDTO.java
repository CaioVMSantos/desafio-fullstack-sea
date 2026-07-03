package com.sea.desafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    @NotBlank(message = "O nome é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9 ]+$", message = "O nome deve conter apenas letras, números e espaços")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório!")
    private String cpf;

    @NotBlank(message = "O endereço é obrigatório!")
    @Valid
    private EnderecoRequestDTO endereco;

    @NotEmpty(message = "Pelo menos um telefone deve ser cadastrado!")
    @Valid
    private List<TelefoneRequestDTO> telefones;

    @NotEmpty(message = "Pelo menos um email deve ser cadastrado!")
    @Valid
    private List<EmailRequestDTO> emails;

}
