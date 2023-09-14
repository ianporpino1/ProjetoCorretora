package com.corretora.dto;

import com.corretora.model.Acao;
import com.corretora.model.Transacao;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

public class AcaoDTO implements Serializable{
    private String ticker;
    private double preco;

    private int quantidade;

    public AcaoDTO(String ticker,int quantidade, double preco) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public AcaoDTO() {
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Transacao toTransacao(){
        Transacao transacao = new Transacao();
        Acao acao = new Acao(this.getTicker(),this.getPreco());
        transacao.setAcao(acao);

        transacao.setQuantidade(this.getQuantidade());

        transacao.setTotal();
        return transacao;
    }


}
