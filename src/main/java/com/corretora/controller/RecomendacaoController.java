package com.corretora.controller;

import com.corretora.dto.Result;
import com.corretora.dto.Root;
import com.corretora.excecao.AcaoInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
public class RecomendacaoController {

    @Value("${apiKey}")
    private String apiKey;

    Result result;

    @GetMapping("/recomendacao")
    public String recomendacaoIndex(Model model){

        return "recomendacaoIndex";
    }


    @PostMapping("/recomendacao/informacoes")
    public String getRecomendacaoAcao(Model model,@RequestParam String ticker) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        try{

            callAcaoApi(ticker,model);

        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }

        //retorno(lista de informacoes a serem exibidas) :chama o service para processamento, passando result.
        //model.addAtributte("listaInformacoes",);

        return "recomendacaoAcao";

    }



    private void callAcaoApi(String ticker,Model model) throws JsonProcessingException, AcaoInvalidaException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject("https://brapi.dev/api/quote/" + ticker + "?token=" + apiKey, String.class);
            ObjectMapper om = new ObjectMapper();
            Root root = om.readValue(response, Root.class);

            result = root.results.get(0);

            model.addAttribute("symbol", result.symbol);
            model.addAttribute("price", result.regularMarketPrice);

            System.out.println(result.symbol);


        } catch (HttpClientErrorException he) {
            throw new AcaoInvalidaException("Codigo: " + ticker + " Nao é uma Acao Valida");
        } catch (UnrecognizedPropertyException upe) {
            throw new AcaoInvalidaException("Ticker Obrigatorio");
        }
    }


}
