package com.corretora.controller;

import com.corretora.dto.PosicaoDTO;
import com.corretora.service.PosicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.corretora.service.TransacaoService;

import java.util.List;

@Controller
public class PortifolioController {

    @Autowired
    private PosicaoService posicaoService;
    
    @Autowired 
    private TransacaoService transacaoService;

    @GetMapping("/portifolio")
    public String showPortifolio(Model model){
       List<PosicaoDTO> posicoesList  = posicaoService.findFormattedPosicoes();
       double totalHoldings =0;
       for(PosicaoDTO posicao : posicoesList){
           totalHoldings += posicao.getValorTotal();
       }

       model.addAttribute("posicoesList", posicoesList);
       
       String strSaldo = String.format("%.2f", transacaoService.getSaldo());
       model.addAttribute("saldo", strSaldo);
       
       String strTotalHoldings = String.format("%.2f", totalHoldings);
       model.addAttribute("totalHoldings", strTotalHoldings);

       return "portifolio";
    }

    @GetMapping("")
    public String index(){
        return "redirect:/portifolio";
    }

}
