package com.sea.desafiobackend.controller;

import com.sea.desafiobackend.domain.Usuario;
import com.sea.desafiobackend.dto.request.LoginRequestDTO;
import com.sea.desafiobackend.dto.request.RegistroRequestDTO;
import com.sea.desafiobackend.dto.response.TokenResponseDTO;
import com.sea.desafiobackend.repository.UsuarioRepository;
import com.sea.desafiobackend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> autenticar(@RequestBody @Valid LoginRequestDTO dto) {
        UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenResponseDTO(token, "Bearer"));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid RegistroRequestDTO dto) {
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        String senhaEncriptada = new BCryptPasswordEncoder().encode(dto.getSenha());
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dto.getLogin());
        novoUsuario.setSenha(senhaEncriptada);
        novoUsuario.setPerfil(dto.getPerfil());
        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}