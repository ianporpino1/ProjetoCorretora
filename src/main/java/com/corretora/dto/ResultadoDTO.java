package com.corretora.dto;


import java.sql.Date;

public class ResultadoDTO {

    private String ativo;

    private double resultado;

    private double resultadoPorcentagem;

    private Date data;

    public ResultadoDTO(String ativo, double resultado, double resultadoPorcentagem, Date data) {
        this.ativo = ativo;
        this.resultado = resultado;
        this.resultadoPorcentagem = resultadoPorcentagem;
        this.data = data;

    }

    public ResultadoDTO(){};

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
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

    public void setResultadoPorcentagem(double resultadoPorcentagem) {
        this.resultadoPorcentagem = resultadoPorcentagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}