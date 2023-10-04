package com.corretora.model;

import com.corretora.dto.AcaoDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "Transacao")
public class Transacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Acao acao;

    private int quantidade;

    private double totalTransacao;
    private TipoTransacao tipoTransacao;

    private Date data; //data da transacao

    private Long idUsuario;

    public Transacao(){}


    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public double getTotal() {
        return totalTransacao;
    }

    public void setTotalTransacao(double totalTransacao) {
        this.totalTransacao = totalTransacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setTodayData() {
        this.data = Date.valueOf(LocalDate.now());
    }

}
