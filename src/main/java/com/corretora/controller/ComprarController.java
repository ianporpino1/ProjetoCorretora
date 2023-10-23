package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.Acao;
import com.corretora.model.TipoTransacao;
import com.corretora.service.ApiService;
import com.corretora.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class ComprarController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    ApiService apiService;

    private AcaoDTO result;


    @GetMapping("acao/comprar")
    public String pesquisaComprarAcao(Model model) {
        model.addAttribute("acao");
        return "formComprarAcao";
    }

    @PostMapping("acao/comprar")
    public String getComprarAcao(Model model,@RequestParam String ticker, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        try{

            result = apiService.callApiQuote(ticker);

            result.ticker = result.ticker.toUpperCase();
            model.addAttribute("symbol",result.ticker);
            model.addAttribute("price",result.price);


        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }

        redirectAttributes.addAttribute("ticker", result.ticker);
        return "redirect:/acao/comprar/{ticker}";

    }

    @PostMapping("/acaoComprar")
    public String comprar(Model model, @RequestParam String quantidade) {
        model.addAttribute("quantidade", quantidade);
        try{

            this.transacaoService.setTransacao(new Acao(result.ticker, result.price),quantidade, TipoTransacao.COMPRA);
            
            double operacaoCompra = Integer.parseInt(quantidade) * result.price;
            this.transacaoService.setTransacao(new Acao(operacaoCompra),"1", TipoTransacao.SAIDA);

        }catch (QuantidadeInvalidaException qie){
            model.addAttribute("errorMessage",qie.getMessage());
            return "error/quantidadeError";
        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());

            return "error/acaoError";
        }

        return "redirect:/portifolio";
    }

    @GetMapping("/acao/comprar/{ticker}")
    public String showComprarAcao(Model model) {
        model.addAttribute("symbol",result.ticker);
        model.addAttribute("price",result.price);
        return "comprarAcao";
    }


}
