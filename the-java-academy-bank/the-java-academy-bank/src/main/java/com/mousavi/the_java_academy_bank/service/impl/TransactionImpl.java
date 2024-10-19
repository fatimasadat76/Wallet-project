package com.mousavi.the_java_academy_bank.service.impl;

import com.mousavi.the_java_academy_bank.dto.TransactionDto;
import com.mousavi.the_java_academy_bank.entity.Transaction;
import com.mousavi.the_java_academy_bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("Success")
                .build();
        transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully");



    }
}
