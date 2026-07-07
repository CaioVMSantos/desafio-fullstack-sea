package com.sea.desafiobackend.controller;

import com.sea.desafiobackend.domain.Usuario;
import com.sea.desafiobackend.dto.response.UsuarioResponseDTO;
import com.sea.desafiobackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        // Busca todos, converte para DTO e devolve na lista
        List<UsuarioResponseDTO> usuarios = usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        // Verifica se o usuário existe antes de deletar
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        usuarioRepository.deleteById(id);

        // Retorna 204 No Content (Padrão REST para deleção com sucesso)
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
}