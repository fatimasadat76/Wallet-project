package com.mousavi.the_java_academy_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

 @Data
 @AllArgsConstructor
 @NoArgsConstructor


public class CreditDebitRequest {

    private String accountNumber;
    private String iban;
    private BigDecimal amount;

 }
