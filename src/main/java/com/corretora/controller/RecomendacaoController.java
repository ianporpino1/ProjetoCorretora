package com.corretora.controller;

import com.corretora.dto.Result;
import com.corretora.dto.Root;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.service.ApiService;
import com.corretora.service.RecomendacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RecomendacaoService recomendacaoService;

    @Autowired
    ApiService apiService;

    Result result;

    @GetMapping("/recomendacao")
    public String recomendacaoIndex(Model model){

        return "recomendacaoIndex";
    }


    @PostMapping("/recomendacao/informacoes")
    public String getRecomendacaoAcao(Model model,@RequestParam String ticker) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        try{

            apiService.callApi(ticker);

        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }

        //retorno(lista de informacoes a serem exibidas) :chama o service para processamento, passando result.
        //model.addAtributte("listaInformacoes",);

        return "recomendacaoAcao";

    }


}
