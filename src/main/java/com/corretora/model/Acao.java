package com.corretora.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class Acao implements Serializable {

    private String ticker; //encapsulo em AcaoDTO??
    private double preco;//encapsulo em AcaoDTO??


    public Acao() {
    }
    
    public Acao(double preco) {
        this.preco = preco;
    }
    
    public Acao(String ticker, double preco) {
        this.ticker = ticker;
        this.preco = preco;
    }


    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean isTickerNull() {
    	return ticker == null;
    }
}
