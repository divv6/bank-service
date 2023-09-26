package com.example.restfulbankservice.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferModel {

    private Long from;
    private Long to;
    private BigDecimal amount;
    private Integer pin;
}
