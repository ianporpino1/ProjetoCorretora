package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.Result;

import com.corretora.dto.Root;
import com.corretora.model.TipoTransacao;
import com.corretora.model.Transacao;
import com.corretora.service.PosicaoService;
import com.corretora.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:application-dev.properties")
@Controller
@Validated
public class AcaoController {

    @Autowired
    private TransacaoService transacaoService;
    @Autowired
    private PosicaoService posicaoService;
    private Result result;

    @Value("${apiKey}")
    private String apiKey;

    @GetMapping("acao/comprar")
    public String pesquisaComprarAcao(Model model) {
        model.addAttribute("acao");
        return "formComprarAcao";
    }

    @PostMapping("acao/comprar")
    public String getComprarAcao(Model model,@RequestParam @NotBlank(message = "TICKER OBRIGATORIO") String ticker) throws JsonProcessingException, ValidationException {

        model.addAttribute("ticker",ticker);
        String responseAPI = callAcaoApi(ticker,model);

        return "comprarAcao";

    }

    @PostMapping("/acaoComprar")
    public String comprar(Model model, @RequestParam @Min(1) @NotBlank(message = "QUANTIDADE OBRIGATORIA OU MAIOR QUE 0") String quantidade) throws ValidationException{
        model.addAttribute("quantidade", quantidade);

        this.transacaoService.setTransacao(result,quantidade, TipoTransacao.COMPRA);


        return "redirect:/portifolio";
    }


    @GetMapping("acao/vender")
    public String pesquisaVenderAcao(Model model){
        List<String> tickers = posicaoService.getTickers();
        model.addAttribute("tickers",tickers);

        return "formVenderAcao";
    }


    @PostMapping("acao/vender")
    public String getVenderAcao(Model model, @RequestParam String ticker) throws JsonProcessingException, ValidationException {
        model.addAttribute("ticker",ticker);
        String responseAPI = callAcaoApi(ticker,model);

        return "venderAcao";
    }

    @PostMapping("acao/acaoVender")
    public String vender(Model model, @RequestParam @Min(value = 1,message = "QUANTIDADE MAIOR QUE 0") @NotBlank(message = "QUANTIDADE OBRIGATORIA")String quantidade) throws ValidationException{
        model.addAttribute("quantidade", quantidade);

        this.transacaoService.setTransacao(result,quantidade, TipoTransacao.VENDA);

        return "redirect:/portifolio";
    }






    public String callAcaoApi(String ticker,Model model) throws JsonProcessingException, HttpClientErrorException {
        RestTemplate restTemplate = new RestTemplate();

        try{
            String response = restTemplate.getForObject("https://brapi.dev/api/quote/"  +ticker + "?token="+ apiKey, String.class);
            ObjectMapper om = new ObjectMapper();
            Root root = om.readValue(response, Root.class);

            result = root.results.get(0);

            model.addAttribute("symbol",result.symbol);
            model.addAttribute("price",result.regularMarketPrice);

            System.out.println(result.symbol);

            return "ok";
        }catch(HttpClientErrorException he){
            return "error/acaoNaoEncontradaError";
        }

    }



    @ExceptionHandler(ValidationException.class)
    public String ValidationErrorHandler(ValidationException ve,Model model) {
        String errorMessage = ve.getMessage();

        model.addAttribute("errorMessage", errorMessage);
        System.out.println(ve.getMessage());

        return "error/validationError";
    }






}
