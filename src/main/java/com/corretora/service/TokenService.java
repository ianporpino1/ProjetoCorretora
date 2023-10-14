package com.corretora.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.corretora.model.Usuario;

@Service
public class TokenService { //inutilizado

	public String geraToken(Usuario u) {
		return JWT.create()
				.withIssuer("Usuarios")
				.withSubject(u.getUsername())
				.withClaim("id", u.getId())
				.withExpiresAt(LocalDateTime.now()
						.plusMinutes(10)
						.toInstant(ZoneOffset.of("-03:00"))
				).sign(Algorithm.HMAC256("secreta"));
	}

	public Object getSubject(String s) {
		return JWT.require(Algorithm.HMAC256("secreta"))
				.withIssuer("Usuarios")
				.build().verify(s).getSubject();
	}

}
