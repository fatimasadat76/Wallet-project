package com.mousavi.the_java_academy_bank.dto;

import com.mousavi.the_java_academy_bank.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BankResponse {

    private String responseCode;
    private String responseMassage;
    private Account account;
    private String accountName;
    private String iban;
    private BigDecimal accountBalance;
    private String accountNumber;

}
