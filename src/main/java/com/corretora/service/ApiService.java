package com.corretora.service;

import com.corretora.dto.Root;
import com.corretora.excecao.AcaoInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
@Service
@PropertySource("classpath:application-dev.properties")
public class ApiService {

    @Value("${apiKey}")
    private String apiKey;

    public Root callApi(String ticker) throws AcaoInvalidaException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        try{
            String response = restTemplate.getForObject("https://brapi.dev/api/quote/"  +ticker + "?token="+ apiKey, String.class);
            ObjectMapper om = new ObjectMapper();
            return om.readValue(response, Root.class);

        }catch(HttpClientErrorException he){
            throw new AcaoInvalidaException("Codigo: " + ticker + " Nao é uma Acao Valida");
        }catch (UnrecognizedPropertyException upe){
            throw new AcaoInvalidaException("Ticker Obrigatorio");
        }
    }
}
