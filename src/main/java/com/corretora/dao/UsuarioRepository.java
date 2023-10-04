package com.corretora.dao;

import com.corretora.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
	UserDetails findByLogin(String login);
}
