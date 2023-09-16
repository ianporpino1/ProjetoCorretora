package com.corretora.dto;

import com.corretora.model.Acao;
import com.corretora.model.Transacao;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransacaoResumo { //SOON TO BE DEPRECATED(TALVEZ N, USAR PARA FORMAR HISTORICO)
    private String ticker;
    private double preco;

    private double totalTransacao;
    private BigDecimal quantidade;

    public TransacaoResumo(String ticker, BigDecimal quantidade, double preco, double total) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
        this.totalTransacao = total;
    }

    public TransacaoResumo() {
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }


    public double getTotal() {
        return totalTransacao;
    }

    public void setTotal(double total) {
        this.totalTransacao = total;
    }

}
