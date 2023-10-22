package com.corretora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.Acao;
import com.corretora.model.TipoTransacao;
import com.corretora.service.TransacaoService;

@Controller
@Validated
public class DepositarController {
	
    @Autowired
    private TransacaoService transacaoService;
    
    @GetMapping("/depositar")
    public String formDeposito() {
    	return "formDepositar";
    }
    
    @PostMapping("/depositar") 
    public String depositar(Model model, @RequestParam double valor) {
    	try {
    		
    		this.transacaoService.setTransacao(new Acao(valor), "1", TipoTransacao.ENTRADA);
    		
    	}catch(QuantidadeInvalidaException qie) {
    		model.addAttribute("errorMessage",qie.getMessage());
            return "error/quantidadeError";
    	}catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }
    	
    	return "redirect:/portifolio";
    }
}
