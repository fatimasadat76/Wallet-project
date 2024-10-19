package com.mousavi.the_java_academy_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TransferRequest {

    private  String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
    private String sourceIban;
    private String destinationIban;

}
