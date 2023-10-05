package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.Result;
import com.corretora.dto.Root;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.TipoTransacao;
import com.corretora.service.ApiService;
import com.corretora.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Validated
public class ComprarController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    ApiService apiService;

    //private Result result;

    private AcaoDTO result;


    @GetMapping("acao/comprar")
    public String pesquisaComprarAcao(Model model) {
        model.addAttribute("acao");
        return "formComprarAcao";
    }

    @PostMapping("acao/comprar")
    public String getComprarAcao(Model model,@RequestParam String ticker) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        try{

            result = apiService.callApi(ticker);

            result.ticker = result.ticker.toUpperCase();
            model.addAttribute("symbol",result.ticker);
            model.addAttribute("price",result.price);


        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }

        return "comprarAcao";

    }

    @PostMapping("/acaoComprar")
    public String comprar(Model model, @RequestParam String quantidade) {
        model.addAttribute("quantidade", quantidade);
        try{

            this.transacaoService.setTransacao(result,quantidade, TipoTransacao.COMPRA);

        }catch (QuantidadeInvalidaException qie){
            model.addAttribute("errorMessage",qie.getMessage());
            return "error/quantidadeError";
        }

        return "redirect:/portifolio";
    }

}
