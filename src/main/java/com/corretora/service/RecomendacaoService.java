package com.corretora.service;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.RecomendacaoDTO.Attributes;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecomendacaoService {

    //1/EvEbitda
    public List<Double> processarInformacoes(Attributes attributes){
        ArrayList<Double> informacoes = new ArrayList<>();

        double retornoEmPorcentagemEsperado = (1/attributes.evEbitda)*100;
        retornoEmPorcentagemEsperado = retornoEmPorcentagemEsperado*100;
        retornoEmPorcentagemEsperado = Math.round(retornoEmPorcentagemEsperado);
        retornoEmPorcentagemEsperado = retornoEmPorcentagemEsperado/100;

        double priceEarningsRatio = attributes.lastClosePriceEarningsRatio*100;
        priceEarningsRatio = Math.round(priceEarningsRatio);
        priceEarningsRatio = priceEarningsRatio/100;

        informacoes.add(retornoEmPorcentagemEsperado);
        informacoes.add(priceEarningsRatio);

        return informacoes;
    }
}
