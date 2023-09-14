package com.corretora.dto;

import com.corretora.dto.Result;

import java.util.ArrayList;
import java.util.Date;

public class Root{
    public ArrayList<Result> results;
    public Date requestedAt;
    public String took;
}
