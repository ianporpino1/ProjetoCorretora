package com.corretora.service;


import com.corretora.dao.PosicaoRepository;
import com.corretora.dao.ResultadoRepository;
import com.corretora.model.Posicao;
import com.corretora.model.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;


    public void save(Resultado resultado){
        this.resultadoRepository.save(resultado);
    }


}
