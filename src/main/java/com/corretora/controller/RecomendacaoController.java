package com.corretora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecomendacaoController {

    @GetMapping("/recomendacao")
    public String recomendacaoIndex(Model model){

        return "recomendacaoIndex";
    }
}
