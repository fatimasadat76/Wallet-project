package com.mousavi.the_java_academy_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmailDetails {
    private String recipient;
    private String messageBody;
    private String subjects;
    private String attachment;

}
