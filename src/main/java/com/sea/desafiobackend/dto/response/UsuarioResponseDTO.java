package com.sea.desafiobackend.dto.response;

import com.sea.desafiobackend.domain.Usuario;
import lombok.Getter;

@Getter
public class UsuarioResponseDTO {

    private Long id;
    private String login;
    private String perfil;

    // Construtor que converte a Entidade no DTO automaticamente
    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        // Converte o Enum para String (ex: "ROLE_ADMIN")
        this.perfil = usuario.getPerfil().name();
    }
}