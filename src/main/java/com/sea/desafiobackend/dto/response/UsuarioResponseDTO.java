package com.sea.desafiobackend.dto.response;

import com.sea.desafiobackend.domain.Usuario;
import lombok.Getter;

@Getter
public class UsuarioResponseDTO {

    private Long id;
    private String login;
    private String perfil;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.perfil = usuario.getPerfil().name();
    }
}