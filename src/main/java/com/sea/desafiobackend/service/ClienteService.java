package com.sea.desafiobackend.service;

import com.sea.desafiobackend.domain.Cliente;
import com.sea.desafiobackend.domain.Email;
import com.sea.desafiobackend.domain.Endereco;
import com.sea.desafiobackend.domain.Telefone;
import com.sea.desafiobackend.dto.request.ClienteRequestDTO;
import com.sea.desafiobackend.exception.ResourceNotFoundException;
import com.sea.desafiobackend.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ViaCepService viaCepService;

    @Transactional
    public Cliente salvar(ClienteRequestDTO dto) {

        // 1. Limpeza máscara CPF
        String cpfLimpo = dto.getCpf().replaceAll("\\D", "");

        // 2. Validação de Negócio
        if (!isCpfValido(cpfLimpo)) {
            throw new IllegalArgumentException("CPF informado inválido!");
        }
        if (clienteRepository.existsByCpf(cpfLimpo)) {
            throw new IllegalArgumentException("Já existe um cliente com esse CPF.");
        }

        // 3. Conversão DTO -> Entity
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(cpfLimpo);

        // ENDEREÇO
        String cepLimpo = dto.getEndereco().getCep().replaceAll("\\D", "");

        viaCepService.buscarCep(cepLimpo); // Validação do CEP via API ViaCEP

        Endereco endereco = new Endereco();
        endereco.setCep(cepLimpo);
        endereco.setLogradouro(dto.getEndereco().getLogradouro());
        endereco.setBairro(dto.getEndereco().getBairro());
        endereco.setCidade(dto.getEndereco().getCidade());
        endereco.setUf(dto.getEndereco().getUf());
        endereco.setComplemento(dto.getEndereco().getComplemento());

        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);

        // TELEFONES
        List<Telefone> telefones = dto.getTelefones().stream().map(telDto -> {
            Telefone telefone = new Telefone();
            telefone.setNumero(telDto.getNumero().replaceAll("\\D", ""));
            telefone.setTipo(telDto.getTipo());
            telefone.setCliente(cliente);
            return telefone;
        }).collect(Collectors.toList());
        cliente.setTelefones(telefones);

        // E-MAILS
        List<Email> emails = dto.getEmails().stream().map(emailRequestDTO -> {
            Email email = new Email();
            email.setEmail(emailRequestDTO.getEmail());
            email.setCliente(cliente);
            return email;
        }).collect(Collectors.toList());
        cliente.setEmails(emails);

        // 4. Persistência
        return clienteRepository.save(cliente);

    }

    @Transactional
    public Cliente atualizar(Long id, ClienteRequestDTO dto) {
        Cliente clienteExistente = buscarClientePorId(id);

        String novoCpfLimpo = dto.getCpf().replaceAll("\\D", "");

        if (!clienteExistente.getCpf().equals(novoCpfLimpo)) {
            if (!isCpfValido(novoCpfLimpo)) {
                throw new IllegalArgumentException("O novo CPF informado é inválido.");
            }
            if (clienteRepository.existsByCpf(novoCpfLimpo)) {
                throw new IllegalArgumentException("Este novo CPF já está cadastrado em outro cliente.");
            }
            clienteExistente.setCpf(novoCpfLimpo);
        }

        clienteExistente.setNome(dto.getNome());

        String novoCepLimpo = dto.getEndereco().getCep().replaceAll("\\D", "");
        viaCepService.buscarCep(novoCepLimpo);

        Endereco endereco = clienteExistente.getEndereco();
        endereco.setCep(novoCepLimpo);
        endereco.setLogradouro(dto.getEndereco().getLogradouro());
        endereco.setBairro(dto.getEndereco().getBairro());
        endereco.setCidade(dto.getEndereco().getCidade());
        endereco.setUf(dto.getEndereco().getUf());
        endereco.setComplemento(dto.getEndereco().getComplemento());

        clienteExistente.getTelefones().clear();
        List<Telefone> novosTelefones = dto.getTelefones().stream().map(telDto -> {
            Telefone tel = new Telefone();
            tel.setNumero(telDto.getNumero().replaceAll("\\D", ""));
            tel.setTipo(telDto.getTipo());
            tel.setCliente(clienteExistente);
            return tel;
        }).collect(Collectors.toList());
        clienteExistente.getTelefones().addAll(novosTelefones);

        clienteExistente.getEmails().clear();
        List<Email> novosEmails = dto.getEmails().stream().map(emailDto -> {
            Email email = new Email();
            email.setEmail(emailDto.getEmail());
            email.setCliente(clienteExistente);
            return email;
        }).collect(Collectors.toList());
        clienteExistente.getEmails().addAll(novosEmails);

        return clienteRepository.save(clienteExistente);
    }

    // --- ALGORITMO OFICIAL DE VERIFICAÇÃO DE CPF DA RECEITA FEDERAL ---
    private boolean isCpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            char dig10, dig11;
            int sm, i, r, num, peso;

            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    public void deletarCliente(Long id){
        Cliente cliente = buscarClientePorId(id);
        clienteRepository.delete(cliente);
    }
}
