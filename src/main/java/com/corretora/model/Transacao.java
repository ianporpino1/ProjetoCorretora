package com.corretora.model;

import com.corretora.dto.AcaoDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "Transacao")
public class Transacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Acao acao;

    private int quantidade;
    private double total;
    private String descricao; //OU TIPO: COMPRA OU VENDA

    private Date dataHora; //data da transacao

    private Long idUsuario;

    public Transacao(){}


    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = quantidade * acao.getPreco();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
