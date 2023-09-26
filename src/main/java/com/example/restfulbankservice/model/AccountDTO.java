package com.example.restfulbankservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountDTO {

    private Long number;
    private String name;
    private LocalDateTime created;
    private BigDecimal balance;
}
