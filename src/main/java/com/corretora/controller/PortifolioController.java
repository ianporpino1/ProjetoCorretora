package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.TransacaoResumo;
import com.corretora.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.corretora.service.TransacaoService;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

public class PortifolioController {

    @Autowired
    private TransacaoService transacaoService; //talvez usar sevice e no service q faria essa busca

    @GetMapping("/portifolio")
    public String showPortifolio(Model model){
        //recebe list de acoes
        List<TransacaoResumo> transacaoList =  this.transacaoService.calcularResumoAcoes();


        model.addAttribute("transacaoList", transacaoList);

        return "portifolio";
    }

    @GetMapping("")
    public String index(){
        return "redirect:/portifolio";
    }

}
