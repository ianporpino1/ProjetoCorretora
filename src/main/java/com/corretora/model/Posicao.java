package com.corretora.model;

import jakarta.persistence.*;

import javax.management.ConstructorParameters;
import java.io.Serializable;

@Entity(name = "Posicao")
public class Posicao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Acao acao;

    private int quantidadeTotal;
    private double precoMedio;
    private double valorTotal;
    private StatusPosicao statusPosicao;

    public Posicao() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(double precoMedio) {
        this.precoMedio = precoMedio;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPosicao getStatusPosicao() {
        return statusPosicao;
    }

    public void setStatusPosicao() {
        if(this.getQuantidadeTotal() == 0){
            this.statusPosicao= StatusPosicao.FECHADA;
        }
        else if(this.getQuantidadeTotal() >0){
            this.statusPosicao= StatusPosicao.COMPRADA;
        }
        else{
            this.statusPosicao= StatusPosicao.VENDIDA;
        }
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidade) {
        this.quantidadeTotal = quantidade;
    }


    public Posicao atualizarPosicao(int quantidade, double valor){
        this.quantidadeTotal += quantidade;
        this.valorTotal += valor;
        if(quantidadeTotal==0){
            this.setQuantidadeTotal(0);
        }
        else{
            this.precoMedio = (this.valorTotal / this.quantidadeTotal);
        }


        this.setStatusPosicao();

        return this;
    }

}
