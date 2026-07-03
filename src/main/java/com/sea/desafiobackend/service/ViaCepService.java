package com.sea.desafiobackend.service;

import com.sea.desafiobackend.dto.response.ViaCepResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    public ViaCepResponseDTO buscarCep(String cep){
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();

        try{
            ViaCepResponseDTO response = restTemplate.getForObject(url, ViaCepResponseDTO.class);

            if(response != null && Boolean.TRUE.equals(response.getErro())){
                throw new IllegalArgumentException("O CEP informado não existe!");
            }

            return response;

        } catch (IllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new IllegalArgumentException("Erro ao consultar o CEP. Por favor, verifique o CEP e tente novamente.");
        }
    }

}
