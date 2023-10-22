package com.corretora.controller;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.PosicaoDTO;
import com.corretora.dto.TransacaoResumo;
import com.corretora.model.Posicao;
import com.corretora.model.Transacao;
import com.corretora.service.PosicaoService;
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
       
       model.addAttribute("saldo", transacaoService.getSaldo());

       model.addAttribute("totalHoldings", totalHoldings);

       return "portifolio";
    }

    @GetMapping("")
    public String index(){
        return "redirect:/portifolio";
    }

}
