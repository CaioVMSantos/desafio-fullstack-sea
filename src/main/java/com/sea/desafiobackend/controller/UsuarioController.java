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
        List<UsuarioResponseDTO> usuarios = usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        usuarioRepository.deleteById(id);

        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
}