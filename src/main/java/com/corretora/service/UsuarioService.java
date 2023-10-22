package com.corretora.service;

import com.corretora.dao.UsuarioRepository;
import com.corretora.dto.UsuarioDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }

    public Usuario configUser(UsuarioDTO userDTO) throws AcaoInvalidaException{
    	if(this.findByUsername(userDTO.getUsername()) == null){

    		String hashPassword = new BCryptPasswordEncoder().encode(userDTO.password);

            return new Usuario(userDTO.firstName, userDTO.lastName, userDTO.username, hashPassword);

        } else {
        	throw new AcaoInvalidaException("Este username não está disponível");
        }
    }

    public void save(Usuario newUser) {
        usuarioRepository.save(newUser);
    }
}
