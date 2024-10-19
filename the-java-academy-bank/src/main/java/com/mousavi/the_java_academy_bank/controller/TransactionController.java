package com.mousavi.the_java_academy_bank.controller;

import com.itextpdf.text.DocumentException;
import com.mousavi.the_java_academy_bank.entity.Transaction;
import com.mousavi.the_java_academy_bank.service.impl.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor

public class TransactionController {

    private BankStatement bankStatement;

    @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam  String accountNumber
            ,@RequestParam String startDate
             ,@RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return  bankStatement.generateStatement(accountNumber, startDate,endDate);


    }
}
