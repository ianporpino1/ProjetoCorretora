package com.corretora.service;

import com.corretora.dao.PosicaoRepository;
import com.corretora.dto.PosicaoDTO;
import com.corretora.dto.TransacaoResumo;
import com.corretora.model.Posicao;
import com.corretora.model.StatusPosicao;
import com.corretora.model.TipoTransacao;
import com.corretora.model.Transacao;
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
    //teste


    public Posicao findPosicaoByTicker(String ticker){
        return this.posicaoRepository.findPosicaoByTicker(ticker);
    }

    public void savePosicao(Posicao posicao){
        this.posicaoRepository.save(posicao);
    }

    public List<String> getTickers(){ return this.posicaoRepository.getTickers();}

    public void setPosicao(Transacao transacao){
        Posicao posicao = new Posicao();

        posicao.setAcao(transacao.getAcao());
        posicao.setQuantidadeTotal(transacao.getQuantidade());
        posicao.setPrecoMedio(posicao.getAcao().getPreco());
        posicao.setValorTotal(transacao.getTotal());

        posicao.setStatusPosicao();

        this.savePosicao(posicao);
    }


    public List<PosicaoDTO> findAllFormatted(){
        List<Object[]> objPosicoes = this.posicaoRepository.findAllFormatted();
        List<PosicaoDTO> posicoesList = formatPosicao(objPosicoes);

        return posicoesList;
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
    public void atualizarPosicaoVenda(Transacao transacao, Posicao posicao) {
        int novaQuantidade = -(transacao.getQuantidade()) + posicao.getQuantidadeTotal();
        posicao.setQuantidadeTotal(novaQuantidade);

        double resultado = (transacao.getAcao().getPreco() - posicao.getPrecoMedio()) * transacao.getQuantidade(); //talvez criar classe resultado

        double total = posicao.getValorTotal() + transacao.getTotal()  +  resultado;
        posicao.setValorTotal(total);

        System.out.println("RESULTADO: " + resultado);

        posicao.setStatusPosicao();



        if(posicao.getQuantidadeTotal() == 0){

            posicaoRepository.deleteByTicker(posicao.getAcao().getTicker());
        }
        else{
            posicaoRepository.save(posicao);
        }


    }



}
