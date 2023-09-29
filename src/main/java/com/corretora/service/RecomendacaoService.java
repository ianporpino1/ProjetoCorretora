package com.corretora.service;

import com.corretora.dto.AcaoDTO;
import com.corretora.dto.Result;
import org.springframework.stereotype.Service;

@Service
public class RecomendacaoService {

    //GRAHAMS FORMULA AND PETER LYNCHS

    //PARAMETROS (GRAHAM): EPS, EPS GROWTH%, GROWTH MULTIPLIER(1), BOND YIELD AVERAGE%(7), CURRENT BOND YIELD%(13), FAIR P/E of a zero growth stock(6).

    //FORMULA V= EPS*()


    //PARAMETROS (P. LYNCH): EPS GROWTH%, DY%, P/E


    //API CALL STOCK FULL INFROMATION YH Finance Complete

    public double calcular(AcaoDTO result){
        double eps = result.price;
        double epsGrowth = 0.07;


        double value= (eps * (6 + 1*epsGrowth)*7)/ 12;

        return value;

    }

}
