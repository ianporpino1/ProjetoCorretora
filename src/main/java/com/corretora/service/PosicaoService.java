package com.corretora.service;

import com.corretora.dao.PosicaoRepository;
import com.corretora.dto.PosicaoDTO;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PosicaoService {
    @Autowired
    private PosicaoRepository posicaoRepository;

    @Autowired
    private ResultadoService resultadoService;


    public Posicao findPosicaoByTicker(String ticker){
        return this.posicaoRepository.findPosicaoByTicker(ticker);
    }

    public void save(Posicao posicao){
        this.posicaoRepository.save(posicao);
    }

    public List<String> findTickers(){ return this.posicaoRepository.findTickers();}

    public void setPosicao(Transacao transacao){
        Posicao posicao = new Posicao();

        posicao.setAcao(transacao.getAcao());
        posicao.setQuantidadeTotal(transacao.getQuantidade());
        posicao.setPrecoMedio(posicao.getAcao().getPreco());
        posicao.setValorTotal(transacao.getTotal());

        posicao.setStatusPosicao();

        this.save(posicao);
    }


    public List<PosicaoDTO> findFormattedPosicoes(){
        List<Object[]> objPosicoes = this.posicaoRepository.findFormattedPosicoes();

        return formatPosicao(objPosicoes);
    }

    public List<PosicaoDTO> formatPosicao(List<Object[]> objPosicoes){
        List<PosicaoDTO> posicoesList = new ArrayList<> ();
        for (Object[] objPosicao : objPosicoes) {
            String ticker = (String) objPosicao[0];
            double precoMedio = (double) objPosicao[1];
            int quantidadeTotal = (int) objPosicao[2];
            double valorTotal = (double) objPosicao[3];

            PosicaoDTO posicao = new PosicaoDTO(ticker, quantidadeTotal, precoMedio,valorTotal);
            posicoesList.add(posicao);
        }
        return posicoesList;
    }

    public void atualizarPosicaoCompra(Transacao transacao, Posicao posicao) {
            int novaQuantidade = transacao.getQuantidade() + posicao.getQuantidadeTotal();
            posicao.setQuantidadeTotal(novaQuantidade);

            double total = posicao.getValorTotal() + transacao.getTotal();
            posicao.setValorTotal(total);

            double novoPrecoMedio = (posicao.getValorTotal() / posicao.getQuantidadeTotal());
            posicao.setPrecoMedio(novoPrecoMedio);

            posicao.setStatusPosicao();

            posicaoRepository.save(posicao);

    }

    @Transactional
    public void atualizarPosicaoVenda(Transacao transacao, Posicao posicao) throws QuantidadeInvalidaException {
        if(posicao.getQuantidadeTotal() - transacao.getQuantidade() < 0){
            throw new QuantidadeInvalidaException("QUANTIDADE MAXIMA DE VENDA: "  + posicao.getQuantidadeTotal());
        }
        int novaQuantidade = -(transacao.getQuantidade()) + posicao.getQuantidadeTotal();
        posicao.setQuantidadeTotal(novaQuantidade);

        double resultadoFinanceiro = (transacao.getAcao().getPreco() - posicao.getPrecoMedio()) * transacao.getQuantidade();

        resultadoService.save(new Resultado(posicao.getAcao().getTicker(),resultadoFinanceiro, (resultadoFinanceiro / (transacao.getQuantidade() * posicao.getPrecoMedio()) * 100) ));

        double total = posicao.getValorTotal() + transacao.getTotal()  +  resultadoFinanceiro;
        posicao.setValorTotal(total);

        System.out.println("RESULTADO: " + resultadoFinanceiro);

        posicao.setStatusPosicao();


        if(posicao.getQuantidadeTotal() == 0){
            posicaoRepository.deleteByTicker(posicao.getAcao().getTicker());
        }
        else{
            posicaoRepository.save(posicao);
        }


    }



}
