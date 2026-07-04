package com.sea.desafiobackend.controller;

import com.sea.desafiobackend.domain.Cliente;
import com.sea.desafiobackend.dto.request.ClienteRequestDTO;
import com.sea.desafiobackend.dto.response.ClienteResponseDTO;
import com.sea.desafiobackend.dto.response.EmailResponseDTO;
import com.sea.desafiobackend.dto.response.EnderecoResponseDTO;
import com.sea.desafiobackend.dto.response.TelefoneResponseDTO;
import com.sea.desafiobackend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO dto){
        Cliente clienteSalvo = clienteService.salvar(dto);

        ClienteResponseDTO responseDTO = converterParaResponse(clienteSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    private ClienteResponseDTO converterParaResponse(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId( cliente.getId() );
        dto.setNome( cliente.getNome() );
        // Recoloca a máscara do CPF, como pede no edital: 12345678901 -> 123.456.789-01
        dto.setCpf(cliente.getCpf().replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4"));

        EnderecoResponseDTO enderecoDTO = new EnderecoResponseDTO();
        enderecoDTO.setId( cliente.getEndereco().getId() );
        // Recoloca a máscara do CEP: 12345678 -> 12345-678
        enderecoDTO.setCep( cliente.getEndereco().getCep().replaceFirst("(\\d{5})(\\d{3})", "$1-$2") );
        enderecoDTO.setLogradouro( cliente.getEndereco().getLogradouro() );
        enderecoDTO.setBairro( cliente.getEndereco().getBairro() );
        enderecoDTO.setCidade( cliente.getEndereco().getCidade() );
        enderecoDTO.setUf( cliente.getEndereco().getUf() );
        enderecoDTO.setComplemento( cliente.getEndereco().getComplemento() );
        dto.setEndereco(enderecoDTO);

        dto.setTelefones(cliente.getTelefones().stream().map( tel -> {
            TelefoneResponseDTO telDTO = new TelefoneResponseDTO();
            telDTO.setId( tel.getId() );
            telDTO.setTipo( tel.getTipo() );

            String num = tel.getNumero();
            if (num.length() == 11) {
                telDTO.setNumero(num.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3")); // Celular
            } else if (num.length() == 10) {
                telDTO.setNumero(num.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3")); // Fixo
            } else {
                telDTO.setNumero(num); // Fallback
            }
            return telDTO;
        }).collect(Collectors.toList()));

        dto.setEmails(cliente.getEmails().stream().map( email -> {
            EmailResponseDTO emailDTO = new EmailResponseDTO();
            emailDTO.setId( email.getId() );
            emailDTO.setEmail( email.getEmail() );
            return emailDTO;
        }).collect(Collectors.toList()));

        return dto;

    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar(@RequestParam(required = false) String nome) {
        List<Cliente> clientes = clienteService.listar(nome);

        List<ClienteResponseDTO> response = clientes.stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id){
        Cliente cliente = clienteService.buscarClientePorId(id);
        ClienteResponseDTO responseDTO = converterParaResponse(cliente);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        Cliente clienteAtualizado = clienteService.atualizar(id, dto);
        ClienteResponseDTO responseDTO = converterParaResponse(clienteAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
