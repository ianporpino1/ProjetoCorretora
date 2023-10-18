package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.RecomendacaoDTO.Attributes;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.service.ApiService;
import com.corretora.service.RecomendacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RecomendacaoController {

    @Autowired
    RecomendacaoService recomendacaoService;

    @Autowired
    ApiService apiService;

    Attributes attributes;

    @GetMapping("/recomendacao")
    public String recomendacaoIndex(Model model){

        return "recomendacaoIndex";
    }


    @PostMapping("/recomendacao/informacoes")
    public String getRecomendacaoAcao(Model model,@RequestParam String ticker) throws JsonProcessingException {
        model.addAttribute("ticker",ticker.toUpperCase());
        try{

            attributes =  apiService.callApiValuation(ticker);

        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }

        List<Double> informacoes = recomendacaoService.processarInformacoes(attributes);
        model.addAttribute("informacoes",informacoes);

        return "recomendacaoAcao";

    }


}
