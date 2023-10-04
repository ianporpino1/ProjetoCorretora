package com.corretora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;


@Entity(name = "Resultado")
public class Resultado implements Serializable { //duas formas de fazer, cada venda gera um resultado OU cada ticker tem um aglomerado de resultados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double resultado;

    private double resultadoPorcentegem;

    private String ativo;

    private Date data;

    public Resultado() {
    }

    public Resultado(String ativo, double resultado, double resultadoPorcentegem) {
        this.ativo = ativo;
        this.resultado = resultado;
        this.resultadoPorcentegem = resultadoPorcentegem;
        this.data = Date.valueOf(LocalDate.now());
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

    public double getResultadoPorcentegem() {
        return resultadoPorcentegem;
    }

    public void setResultadoPorcentegem(double volume) {
        this.resultadoPorcentegem = volume;
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
}
