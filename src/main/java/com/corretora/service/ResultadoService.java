package com.corretora.service;


import com.corretora.dao.ResultadoRepository;
import com.corretora.dto.ResultadoDTO;
import com.corretora.model.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    AutorizacaoService autorizacaoService;

    private final double ALIQUOTA_IR_BRASIL=0.15;

    private double imposto;

    public void saveResultado(Resultado resultado){
        this.resultadoRepository.save(resultado);
    }

    public List<ResultadoDTO> findAllResultadoByData(int mes, int ano){
        List<Object[]> objResultados = this.resultadoRepository.findAllResultadoByData(mes,ano,autorizacaoService.LoadUsuarioLogado().getId());
        return formatResultado(objResultados);
    }

    public Resultado findResultadoByName(String ativo){
        return this.resultadoRepository.findResultadoByName(autorizacaoService.LoadUsuarioLogado().getId(),ativo);
    }



    public List<ResultadoDTO> formatResultado(List<Object[]> objResultados){
        List<ResultadoDTO> resultadosList = new ArrayList<>();
        for (Object[] objResultado : objResultados) {
            String ativo = (String) objResultado[0];
            double resultado = (double) objResultado[1];
            double resultadoPorcentegem = (double) objResultado[2];
            Date data = (Date) objResultado[3];

            ResultadoDTO res = new ResultadoDTO(ativo, resultado, resultadoPorcentegem, data);

            resultadosList.add(res);
        }
        return resultadosList;
    }


    public void calcularIR(int mes, int ano) {
        List<ResultadoDTO> resultadosList = findAllResultadoByData(mes,ano);
        double total = calcularResultadoTotal(resultadosList);
        double novoImposto=0;

        Resultado prejuizo = findResultadoByName("prejuizoCompensar");
        if(total > 0){
            novoImposto = total * ALIQUOTA_IR_BRASIL;
            if(prejuizo != null)
                atualizarPrejuizoCompensar(prejuizo,total);
        }
        else if(total < 0){
            setPrejuizoCompensar(prejuizo,total);
        }


        novoImposto = novoImposto*100;
        novoImposto = Math.round(novoImposto);
        novoImposto = novoImposto/100;

        imposto = novoImposto;
    }



    public void setPrejuizoCompensar(Resultado prejuizo, double total){
        if(prejuizo==null){
            Resultado prejuizoCompensar = new Resultado();
            prejuizoCompensar.setAtivo("prejuizoCompensar");
            prejuizoCompensar.setResultado(total);
            prejuizoCompensar.setIdUsuario(autorizacaoService.LoadUsuarioLogado().getId());
            prejuizoCompensar.setData(Date.valueOf(LocalDate.now().plusMonths(1)));
            saveResultado(prejuizoCompensar);

        }
        else{
            atualizarPrejuizoCompensar(prejuizo,total);
        }
    }

    public void atualizarPrejuizoCompensar(Resultado prejuizo, double total){
        if(total > 0)
            prejuizo.setResultado(0);
        else
            prejuizo.setResultado(total);
        saveResultado(prejuizo);
    }


    public double calcularResultadoTotal(List<ResultadoDTO> resultadosList) {
        double total=0;
        for(ResultadoDTO resultado : resultadosList){
            total += resultado.getResultado();
        }
        total = total*100;
        total = Math.round(total);
        total = total/100;
        return total;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }
}