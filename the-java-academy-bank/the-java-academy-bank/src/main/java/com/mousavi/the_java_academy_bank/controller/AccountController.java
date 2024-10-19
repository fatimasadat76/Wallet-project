package com.mousavi.the_java_academy_bank.controller;

import com.mousavi.the_java_academy_bank.dto.AccountRequest;
import com.mousavi.the_java_academy_bank.entity.Account;
import com.mousavi.the_java_academy_bank.service.impl.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
@Tag(name = "User Account Management APIs ")

public class AccountController {

    @Autowired
    AccountService accountService;

    @PutMapping("/{userId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody AccountRequest accountRequest) throws Throwable {
        Account updatedAccount = accountService.updateAccount(accountId, accountRequest);
        return ResponseEntity.ok(updatedAccount);
    }


    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

}
