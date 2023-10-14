package com.corretora.controller;

import com.corretora.dto.PosicaoDTO;
import com.corretora.dto.ResultadoDTO;
import com.corretora.service.PosicaoService;
import com.corretora.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ImpostoRendaController {

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/calculadora-imposto-de-renda")
    public String showCalculadora(Model model){


        return "impostoRenda";
    }


    @PostMapping("/calculadora-imposto-de-renda")
    public String calcularIR(Model model,@RequestParam int mes){
        List<ResultadoDTO> resultadosList = resultadoService.findResultadoByData(mes);
        double valorImposto = resultadoService.calcularIR(resultadosList);
        double resultadoTotal = resultadoService.calcularResultadoTotal(resultadosList);
        model.addAttribute("resultadosList", resultadosList);
        model.addAttribute("valorImposto", valorImposto);
        model.addAttribute("resultadoTotal",resultadoTotal);

        return "impostoCalculado";
    }

}
