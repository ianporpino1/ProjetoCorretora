package com.corretora.service;


import com.corretora.dao.PosicaoRepository;
import com.corretora.dao.ResultadoRepository;
import com.corretora.dto.ResultadoDTO;
import com.corretora.model.Posicao;
import com.corretora.model.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    AutorizacaoService autorizacaoService;


    public void save(Resultado resultado){
        this.resultadoRepository.save(resultado);
    }

    public List<ResultadoDTO> findResultadoByData(int mes){
        List<Object[]> objResultados = this.resultadoRepository.findResultadoByData(mes,autorizacaoService.LoadUsuarioLogado().getId());
        return formatResultado(objResultados);
    }

    public List<ResultadoDTO> formatResultado(List<Object[]> objResultados){
        List<ResultadoDTO> resultadosList = new ArrayList<>();
        for (Object[] objResultado : objResultados) {
            String ativo = (String) objResultado[0];
            double resultado = (double) objResultado[1];
            double resultadoPorcentegem = (double) objResultado[2];

            ResultadoDTO res = new ResultadoDTO(ativo, resultado, resultadoPorcentegem);

            resultadosList.add(res);
        }
        return resultadosList;
    }


    public double calcularIR(List<ResultadoDTO> resultadosList) {
        double imposto = 0;
        double total=0;
        for(ResultadoDTO resultado : resultadosList){
            total += resultado.getResultado();
        }
        if(total > 0)
            imposto = total * 0.15;

        imposto = imposto*100;
        imposto = Math.round(imposto);
        imposto = imposto/100;
        return imposto;
    }

    public void setResultado(Resultado novoResultado){
        save(novoResultado);
    }


    public double calcularResultadoTotal(List<ResultadoDTO> resultadosList) {
        double total=0;
        for(ResultadoDTO resultado : resultadosList){
            total += resultado.getResultado();
        }
        total = total*100;
        total = Math.round(total);
        total = total/100;
        return total;
    }
}