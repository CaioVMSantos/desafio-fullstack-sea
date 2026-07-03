package com.sea.desafiobackend.dto.response;

import com.sea.desafiobackend.enums.TipoTelefone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneResponseDTO {

    private Long id;
    private String numero;
    private TipoTelefone tipo;

}
