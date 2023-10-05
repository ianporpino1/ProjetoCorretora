package com.corretora.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.corretora.service.FiltroService;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {
	
	@Autowired
	private FiltroService filtro;
	
	@Bean
	public SecurityFilterChain configPermissoes(HttpSecurity httpsec) throws Exception {
		return  httpsec
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/logar").permitAll()
						.requestMatchers(HttpMethod.POST, "/registrar").permitAll()
						.anyRequest().authenticated()
				).addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationManager configAutenticacao(AuthenticationConfiguration ac) throws Exception {
		return ac.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder hashuraSenha() {
		return new BCryptPasswordEncoder();
	}
}
