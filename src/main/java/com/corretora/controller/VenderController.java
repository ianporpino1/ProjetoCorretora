package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.Acao;
import com.corretora.model.TipoTransacao;
import com.corretora.service.ApiService;
import com.corretora.service.PosicaoService;
import com.corretora.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Validated
public class VenderController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private PosicaoService posicaoService;

    private AcaoDTO result;

    @Autowired
    ApiService apiService;




    @GetMapping("acao/vender")
    public String pesquisaVenderAcao(Model model){
        List<String> tickers = posicaoService.findTickers();
        model.addAttribute("tickers", tickers);
        return "formVenderAcao";
    }


    @PostMapping("acao/vender")
    public String getVenderAcao(Model model, @RequestParam String ticker) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        try{

            result = apiService.callApiQuote(ticker);

            model.addAttribute("symbol",result.ticker);
            model.addAttribute("price",result.price);

        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }


        return "venderAcao";
    }

    @PostMapping("acao/acaoVender")
    public String vender(Model model, @RequestParam String quantidade){
        model.addAttribute("quantidade", quantidade);
        try{

            this.transacaoService.setTransacao(new Acao(result.ticker, result.price),quantidade, TipoTransacao.VENDA);

        }catch (QuantidadeInvalidaException qie){
            model.addAttribute("errorMessage",qie.getMessage());
            return "error/quantidadeError";
        }


        return "redirect:/portifolio";
    }




}
