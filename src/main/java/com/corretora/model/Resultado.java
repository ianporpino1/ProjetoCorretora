package com.corretora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;


@Entity(name = "Resultado")
public class Resultado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double resultado;

    private double resultadoPorcentagem;

    private String ativo;

    private Date data;

    private Long idUsuario;

    public Resultado() {
    }

    public Resultado(String ativo, double resultado, double resultadoPorcentagem, Long idUsuario) {
        this.ativo = ativo;
        this.resultado = resultado;
        this.resultadoPorcentagem = resultadoPorcentagem;
        this.data = Date.valueOf(LocalDate.now());
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    public double getResultadoPorcentagem() {
        return resultadoPorcentagem;
    }

    public void setResultadoPorcentagemem(double resultadoPorcentagem) {
        this.resultadoPorcentagem = resultadoPorcentagem;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
