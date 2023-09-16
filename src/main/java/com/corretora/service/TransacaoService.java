package com.corretora.service;

import com.corretora.dao.TransacaoRepository;
import com.corretora.dto.Result;
import com.corretora.dto.TransacaoResumo;
import com.corretora.model.Acao;
import com.corretora.model.Posicao;
import com.corretora.model.TipoTransacao;
import com.corretora.model.Transacao;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private  PosicaoService posicaoService;

    public List<Transacao> findAllTransacao(){

        return (List<Transacao>) transacaoRepository.findAll();
    }

    public List<TransacaoResumo> calcularResumoAcoes(){   //DEPRECATED
        List<Object[]> resultados = transacaoRepository.calcularResumoTransacoes();
        List<TransacaoResumo> resumos = createResumo(resultados);

        return resumos;
    }

    public List<TransacaoResumo> createResumo(List<Object[]> resultados){ //DEPRECATED
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

    public void setTransacao(Result result, String quantidade, TipoTransacao tipoTransacao){
        Transacao transacao = new Transacao();
        int intQuantidade = Integer.parseInt(quantidade);
        Acao acao = new Acao(result.symbol,result.regularMarketPrice);
        transacao.setAcao(acao);
        transacao.setTipoTransacao(tipoTransacao);
        if(tipoTransacao.equals(tipoTransacao.VENDA)){
            intQuantidade = -intQuantidade;
        }

        transacao.setQuantidade(intQuantidade);
        transacao.setTotalTransacao();

        this.saveTransacao(transacao);

        this.checkPosicao(transacao);

    }

    public List<String> getTickersTransacao(){

        return this.transacaoRepository.getTickers();
    } //DEPRECATED


    public void checkPosicao(Transacao transacao){
        Posicao posicao = posicaoService.findPosicaoByTicker(transacao.getAcao().getTicker());

        if(posicao == null){
            posicaoService.setPosicao(transacao);
        }
        else{
           atualizarPosicao(transacao);
        }
    }


    public void atualizarPosicao(Transacao transacao){
        System.out.println("ATUALIZANDO POSICAO");

        posicaoService.atualizarPosicao(transacao);

    }



}
