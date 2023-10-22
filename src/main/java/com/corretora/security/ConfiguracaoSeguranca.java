package com.corretora.security;

import com.corretora.service.AutorizacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

	@Autowired
	private AutorizacaoService autorizacaoService;

	
	@Bean
	public SecurityFilterChain configPermissoes(HttpSecurity httpsec) throws Exception {
		httpsec
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/logar", "/registrar").permitAll()
						.anyRequest().authenticated()
				).formLogin(formLogin -> formLogin
				.loginPage("/logar")
				.defaultSuccessUrl("/portifolio", true)
						.permitAll()
				.failureUrl("/logar")

		);

		return httpsec.build();
	}

	@Bean
	public PasswordEncoder hashuraSenha() {
		return new BCryptPasswordEncoder();
	}



}
