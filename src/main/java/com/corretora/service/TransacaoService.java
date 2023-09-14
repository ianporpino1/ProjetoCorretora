package com.corretora.service;

import com.corretora.dao.TransacaoRepository;
import com.corretora.dto.TransacaoResumo;
import com.corretora.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> findAllTransacao(){
        return (List<Transacao>) transacaoRepository.findAll();
    }

    public List<TransacaoResumo> calcularResumoAcoes(){
        List<Object[]> resultados = transacaoRepository.calcularResumoTransacoes();
        List<TransacaoResumo> resumos = createResumo(resultados);

        return resumos;
    }

    public List<TransacaoResumo> createResumo(List<Object[]> resultados){
        List<TransacaoResumo> resumos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            String ticker = (String) resultado[0];
            BigDecimal quantidade = (BigDecimal) resultado[1];
            double precoMedio = (double) resultado[2];
            double total = (double) resultado[3];

            TransacaoResumo resumo = new TransacaoResumo(ticker, quantidade, precoMedio,total);
            resumos.add(resumo);
        }
        return resumos;
    }

    public void saveTransacao(Transacao transacao){

        transacaoRepository.save(transacao);
    }

}
