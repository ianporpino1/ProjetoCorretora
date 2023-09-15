package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.Result;

import com.corretora.dto.Root;
import com.corretora.model.TipoTransacao;
import com.corretora.model.Transacao;
import com.corretora.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@Controller
public class AcaoController {

    @Autowired
    private TransacaoService transacaoService;
    private Result result;

    @GetMapping("acao/comprar")
    public String acaoForm(Model model) {
        //model.addAttribute("acao");
        return "formComprarAcao";
    }

    @PostMapping("acao/comprar")
    public String pesquisaAcao(@RequestParam String ticker, Model model) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        String responseAPI = callAcaoApi(ticker,model);

        return responseAPI;
    }

    @PostMapping("/saveAcao")
    public String saveAcao(Model model, @RequestParam("quantidade") String quantidade){
        model.addAttribute("quantidade", quantidade);
        //TODO excecao se quantidade for vazia

        //AcaoDTO acaoDTO = new AcaoDTO();
        //n eh p ser feito aqui, service provavelmente
        //acaoDTO.setPreco(result.regularMarketPrice);
        //acaoDTO.setTicker(result.symbol);
       // acaoDTO.setQuantidade(Integer.parseInt(quantidade));

        //Transacao transacao = acaoDTO.toTransacao();
        Transacao transacao = this.transacaoService.setTransacao(result,quantidade, TipoTransacao.COMPRA);

        this.transacaoService.saveTransacao(transacao);


        return "redirect:/portifolio";
    }


    @GetMapping("acao/vender")
    public String pesquisaVenderAcao(Model model){
        return "formVenderAcao";
    }


    @PostMapping("acao/vender")
    public String getAcao(Model model, @RequestParam("ticker") String ticker) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        String responseAPI = callAcaoApi(ticker,model);

        return "venderAcao";
    }

    @PostMapping("acao/acaoVender")
    public String vender(Model model, @RequestParam("quantidade") String quantidade){
        model.addAttribute("quantidade", quantidade);

        Transacao transacao = this.transacaoService.setTransacao(result,quantidade, TipoTransacao.VENDA);

        this.transacaoService.saveTransacao(transacao);

        return "redirect:/portifolio";
    }






    public String callAcaoApi(String ticker,Model model) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject("https://brapi.dev/api/quote/"  +ticker + "?token=5wMmBsVdAuX3LXQ1NJvqoH", String.class);

        ObjectMapper om = new ObjectMapper();
        Root root = om.readValue(response, Root.class);

        //TODO handle exception se ticker for vazio

        result = root.results.get(0);
        if(result.regularMarketPrice == 0){
            return "Acao invalida";
        }
        else{
            model.addAttribute("symbol",result.symbol);
            model.addAttribute("price",result.regularMarketPrice);
            System.out.println(result.symbol);
            return "comprarAcao";
        }

    }



}
