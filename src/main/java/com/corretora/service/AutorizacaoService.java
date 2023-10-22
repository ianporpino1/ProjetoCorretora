package com.corretora.service;

import com.corretora.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.corretora.dao.UsuarioRepository;

@Service
public class AutorizacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioService.findByUsername(username);
		if(user != null){
			return user;
		}
		else{
			throw new UsernameNotFoundException("Usuario nao encontrado");
		}
	}

	public Usuario LoadUsuarioLogado(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return usuarioService.findByUsername(username);
	}


}
