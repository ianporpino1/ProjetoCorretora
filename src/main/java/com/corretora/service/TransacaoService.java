package com.corretora.service;

import com.corretora.dao.TransacaoRepository;
import com.corretora.dto.AcaoDTO;
import com.corretora.dto.Result;
import com.corretora.dto.TransacaoResumo;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.*;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private  PosicaoService posicaoService;

    public List<Transacao> findAllTransacao(){

        return (List<Transacao>) transacaoRepository.findAll();
    }

    public List<TransacaoResumo> findFormattedTransacoes(){   //DEPRECATED
        List<Object[]> resultados = transacaoRepository.calcularResumoTransacoes(autorizacaoService.LoadUsuarioLogado().getId());
        List<TransacaoResumo> resumos = createResumo(resultados);

        return resumos;
    }

    public List<TransacaoResumo> createResumo(List<Object[]> resultados){ //DEPRECATED
        List<TransacaoResumo> resumos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            String ticker = (String) resultado[0];
            int quantidade = (int) resultado[1];
            double preco = (double) resultado[2];
            double total = (double) resultado[3];
            int intTipo = (Byte) resultado[4];
            Date data = (Date) resultado[5];

            String tipoTransacao = null;
            if(intTipo == 0){
                tipoTransacao = "COMPRA";
            }
            else if(intTipo == 1){
                tipoTransacao = "VENDA";
            }

            String dataFormatted = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dataFormatted = simpleDateFormat.format(data);

            TransacaoResumo resumo = new TransacaoResumo(ticker, quantidade, preco,total,tipoTransacao,dataFormatted);
            resumos.add(resumo);
        }
        return resumos;
    }

    public void saveTransacao(Transacao transacao){

        transacaoRepository.save(transacao);
    }

    public void setTransacao(AcaoDTO result, String quantidade, TipoTransacao tipoTransacao) throws QuantidadeInvalidaException {
        Transacao transacao = new Transacao();
        if(quantidade == ""){
            throw new QuantidadeInvalidaException("Quantidade Obrigatoria");
        }
        int intQuantidade = Integer.parseInt(quantidade);
        if(intQuantidade <= 0){
            throw new QuantidadeInvalidaException("Quantidade Deve Ser Maior que 0");
        }

        Acao acao = new Acao(result.ticker, result.price);
        transacao.setAcao(acao);
        transacao.setTipoTransacao(tipoTransacao);
        transacao.setQuantidade(intQuantidade);
        transacao.setTodayData();
        transacao.setIdUsuario(autorizacaoService.LoadUsuarioLogado().getId());


        if(tipoTransacao == TipoTransacao.VENDA){
            double total = -(intQuantidade) * acao.getPreco();
            transacao.setTotalTransacao(total);
        }
        else{
            transacao.setTotalTransacao(intQuantidade * acao.getPreco());
        }

        this.checkPosicao(transacao);


        this.saveTransacao(transacao);

    }

    public List<String> findTickersTransacao(){

        return this.transacaoRepository.findTickers();
    } //DEPRECATED


    public void checkPosicao(Transacao transacao) throws QuantidadeInvalidaException {
        Posicao posicao = posicaoService.findPosicaoByTicker(transacao.getAcao().getTicker());

        if(posicao == null){
            posicaoService.setPosicao(transacao);
        }
        else if (transacao.getTipoTransacao() == TipoTransacao.COMPRA ){
           posicaoService.atualizarPosicaoCompra(transacao,posicao);
        }
        else if (transacao.getTipoTransacao() == TipoTransacao.VENDA ){
            posicaoService.atualizarPosicaoVenda(transacao,posicao);
        }
    }
}
