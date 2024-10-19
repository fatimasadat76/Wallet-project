package com.mousavi.the_java_academy_bank.service.impl;

import com.mousavi.the_java_academy_bank.dto.*;
import com.mousavi.the_java_academy_bank.entity.Account;
import com.mousavi.the_java_academy_bank.entity.User;
import com.mousavi.the_java_academy_bank.exception.ResourceNotFoundException;
import com.mousavi.the_java_academy_bank.repository.AccountRepository;
import com.mousavi.the_java_academy_bank.utils.AccountUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    @Autowired
    EmailService emailService;
    @Autowired
    AccountRepository accountRepository;
    private Long accountId;

    @Override
    public BankResponse createAccount(AccountRequest accountRequest) {

            Account newAccount = Account.builder()
                    .accountNumber(AccountUtils.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .iban(AccountUtils.generateAccountIban(AccountUtils.generateAccountNumber()))
                    .build();
            Account saveAccount = (Account) AccountRepository.save(newAccount);
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(saveAccount.getEmail())
                    .subjects("Account creation")
                    .messageBody("Congratulations! Your Account Has Successfully Created.\n Your Account Details : \n" +
                            "Account Name: " + saveAccount.getAccountName()
                            + "\n Account iban :" + saveAccount.getIban() + "\n Account Name :" + saveAccount.getAccountName())
                    .build();
            emailService.sendEmailAllert(emailDetails);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                    .responseMassage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .account(Account.builder()
                            .accountBalance(saveAccount.getAccountBalance())
                            .accountNumber(saveAccount.getAccountNumber())
                            .iban(saveAccount.getIban())
                            .accountId(saveAccount.getAccountName())
                            .build())
                    .build();

        }
    @Override
    public BankResponse deleteAccount(Long accountId) {


            Account newAccount = Account.builder()
                    .accountNumber(AccountUtils.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .iban(AccountUtils.generateAccountIban(AccountUtils.generateAccountNumber()))
                    .build();
            Account saveAccount = (Account) AccountRepository.save(newAccount);
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(saveAccount.getEmail())
                    .subjects("Account creation")
                    .messageBody("Congratulations! Your Account Has Successfully Created.\n Your Account Details : \n" +
                            "Account Name: " + saveAccount.getAccountName()
                            + "\n Account iban :" + saveAccount.getIban() + "\n Account Name :" + saveAccount.getAccountName())
                    .build();
            emailService.sendEmailAllert(emailDetails);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                    .responseMassage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .account(Account.builder()
                            .accountBalance(saveAccount.getAccountBalance())
                            .accountNumber(saveAccount.getAccountNumber())
                            .iban(saveAccount.getIban())
                            .accountId(saveAccount.getAccountName())
                            .build())
                    .build();

    }

    @Override
    public Account updateAccount(Long accountId, AccountRequest accountRequest) throws Throwable {
        Account account = (Account) AccountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAccountId(account.getAccountId());
            account.setAccountBalance(account.getAccountBalance());
            account.setAccountName(account.getAccountName());
            account.setAccountNumber(account.getAccountNumber());

            return (Account) AccountRepository.save(account);
        }
}
