package com.corretora.dto;

public class ResultadoDTO {

    private String ativo;

    private double resultado;

    private double resultadoPorcentagem;

    public ResultadoDTO(String ativo, double resultado, double resultadoPorcentagem) {
        this.ativo = ativo;
        this.resultado = resultado;
        this.resultadoPorcentagem = resultadoPorcentagem;

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

}