package com.corretora.controller;

import com.corretora.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.corretora.dao.UsuarioRepository;
import com.corretora.dto.LoginDTO;
import com.corretora.dto.UsuarioDTO;
import com.corretora.model.Usuario;
import com.corretora.service.TokenService;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {
	

	@Autowired
	private UsuarioService usuarioService;

	

	
	@PostMapping("/registrar")
	public String registrarUsuario(@ModelAttribute @Valid UsuarioDTO userDTO) {
	//public ResponseEntity<?> registarUsuario(@ModelAttribute @Valid UsuarioDTO user) {
		if (this.usuarioService.findByUsername(userDTO.username) != null) {
			return ResponseEntity.badRequest().build().toString();
		}

		Usuario newUser = usuarioService.configUser(userDTO);

		usuarioService.save(newUser);
		
		//return "registrar";
		return "redirect:/logar";
	}

	@GetMapping("/registrar")
	public String registrar(Model model){
		model.addAttribute("UsuarioDTO", new UsuarioDTO());
		return "registrar";
	}

	@GetMapping("/logar")
	public String logar(Model model){
		model.addAttribute("LoginDTO", new LoginDTO());
		return "logar";
	}

}
