package com.corretora.dto;

import com.corretora.dto.Result;
import jakarta.validation.constraints.NotBlank;


import java.util.ArrayList;
import java.util.Date;

public class Root{
    @NotBlank
    public ArrayList<Result> results;
    public Date requestedAt;
    public String took;
}
