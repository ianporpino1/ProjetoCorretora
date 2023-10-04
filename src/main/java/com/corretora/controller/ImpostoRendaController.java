package com.corretora.controller;

import com.corretora.dto.PosicaoDTO;
import com.corretora.service.PosicaoService;
import com.corretora.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ImpostoRendaController {

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/calculadora-imposto-de-renda")
    public String showCalculadora(Model model){


        return "impostoRenda";
    }

}
