package com.corretora.controller;

import com.corretora.dto.TransacaoResumo;
import com.corretora.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HistoricoController {

    @Autowired
    private TransacaoService transacaoService;


    @GetMapping("/historico")
    public String showHistorico(Model model){
        List<TransacaoResumo> transacoesList = transacaoService.findFormattedTransacoes();
        model.addAttribute("transacoesList", transacoesList);
        return "historico";
    }
}
