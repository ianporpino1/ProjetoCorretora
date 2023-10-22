package com.corretora.controller;

import com.corretora.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.corretora.dto.UsuarioDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.model.Usuario;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {
	

	@Autowired
	private UsuarioService usuarioService;

	
	@PostMapping("/registrar")
	public String registrarUsuario(@ModelAttribute @Valid UsuarioDTO userDTO) {
		try {
			
			Usuario newUser = usuarioService.configUser(userDTO);

			usuarioService.save(newUser);
			
		} catch(AcaoInvalidaException aie) {
			return "error/acaoError";
		}
		
		return "redirect:/logar";
	}

	@GetMapping("/registrar")
	public String registrar(Model model){
		model.addAttribute("UsuarioDTO", new UsuarioDTO());
		return "registrar";
	}

	@GetMapping("/logar")
	public String logar(Model model)  {


		return "logar";
	}

}
