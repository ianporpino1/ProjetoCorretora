package com.corretora.service;

import com.corretora.dto.AcaoDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.io.IOException;


@Service
@PropertySource("classpath:application-dev.properties")
public class ApiService {

    @Value("${apiKey}")
    private String apiKey;

    public AcaoDTO callApi(String ticker) throws AcaoInvalidaException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", apiKey);
        headers.add("X-RapidAPI-Host","realstonks.p.rapidapi.com");
        HttpEntity<Object> entity=new HttpEntity<Object>(headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange("https://realstonks.p.rapidapi.com/"  +ticker, HttpMethod.GET,entity,String.class);

            ObjectMapper om = new ObjectMapper();

            System.out.println(response);
            AcaoDTO acao = om.readValue(response.getBody(), AcaoDTO.class);
            acao.ticker = ticker;
            return acao;

        }catch(HttpClientErrorException he){
            throw new AcaoInvalidaException("Codigo: " + ticker + " Nao é uma Acao Valida");
        }catch (UnrecognizedPropertyException upe){
            throw new AcaoInvalidaException("Ticker Obrigatorio");
       }
    }
}
