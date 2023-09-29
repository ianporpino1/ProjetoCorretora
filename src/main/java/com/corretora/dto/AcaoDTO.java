package com.corretora.dto;

import com.corretora.model.Acao;
import com.corretora.model.Transacao;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

public class AcaoDTO{ //INUTILIZADO
    public double price;
    public double change_point;
    public double change_percentage;
    public String total_vol;
    public String ticker;
}
