package com.sea.desafiobackend.dto;

import com.sea.desafiobackend.enums.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneRequestDTO {

    @NotBlank(message = "O número de telefone é obrigatório!")
    private String numero;

    @NotNull(message = "O tipo de telefone (RESIDENCIAL, COMERCIAL, CELULAR) é obrigatório!")
    private TipoTelefone tipo;

}
