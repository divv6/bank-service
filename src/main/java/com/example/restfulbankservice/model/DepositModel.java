package com.example.restfulbankservice.model;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class DepositModel {

    @NonNull
    private Long number;
    @NonNull
    private BigDecimal amount;
    private Integer pin;
}
