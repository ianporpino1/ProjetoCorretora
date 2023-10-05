package com.corretora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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
	private AuthenticationManager validacaoLogin;
	@Autowired
	private UsuarioRepository repo;
	@Autowired
	private TokenService token;
	
	@PostMapping("/logar")
	//public String logarUsuario(@RequestBody @Valid LoginDTO login) {
	public ResponseEntity<?> logarUsuario(@RequestBody @Valid LoginDTO login) {
		UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(login.login, login.password);
		var auth = this.validacaoLogin.authenticate(userPassAuthToken);
		Usuario user = (Usuario) auth.getPrincipal();
		
		//return "logar";
		return ResponseEntity.ok(token.geraToken(user));
	}
	
	@PostMapping("/registrar")
	//public String registrarUsuario(@RequestBody @Valid UsuarioDTO login) {
	public ResponseEntity<?> registarUsuario(@RequestBody @Valid UsuarioDTO user) {
		if (this.repo.findByLogin(user.login) != null) {
			return ResponseEntity.badRequest().build();
		} 
		
		String hashPassword = new BCryptPasswordEncoder().encode(user.password);
		Usuario newUser = new Usuario(user.firstName, user.lastName, user.login, hashPassword);
		this.repo.save(newUser);
		
		//return "registrar";
		return ResponseEntity.ok().build();
	}
}
