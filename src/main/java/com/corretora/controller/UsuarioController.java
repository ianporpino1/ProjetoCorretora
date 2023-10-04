package com.corretora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.corretora.dao.UsuarioRepository;
import com.corretora.dto.UsuarioDTO;
import com.corretora.model.Usuario;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {
	
	@Autowired
	private AuthenticationManager validacaoLogin;
	@Autowired
	private UsuarioRepository repo;
	
	@PostMapping("/logar")
	public ResponseEntity<?> logarUsuario(@RequestBody @Valid UsuarioDTO user) {
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user.login, user.password);
		Authentication a = validacaoLogin.authenticate(upat);
		
		if (a.isAuthenticated()) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registarUsuario(@RequestBody @Valid UsuarioDTO user) {
		if (this.repo.findByLogin(user.login) != null) {
			return ResponseEntity.badRequest().build();
		} 
		
		String senhaHash = new BCryptPasswordEncoder().encode(user.password);
		Usuario novoUser = new Usuario(user.firstName, user.lastName, user.login, senhaHash);
		this.repo.save(novoUser);
		
		return ResponseEntity.ok().build();
	}
}
