package com.mousavi.the_java_academy_bank.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EnquiryRequest {

    private String accountNumber;
    private String iban;


}
