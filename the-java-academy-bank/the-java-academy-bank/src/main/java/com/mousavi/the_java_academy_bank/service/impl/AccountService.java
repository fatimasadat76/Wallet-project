package com.mousavi.the_java_academy_bank.service.impl;

import com.mousavi.the_java_academy_bank.dto.*;
import com.mousavi.the_java_academy_bank.entity.Account;

public interface AccountService {

    BankResponse createAccount(AccountRequest accountRequest);
    BankResponse deleteAccount(Long accountId);
    Account updateAccount(Long accountId, AccountRequest accountRequest) throws Throwable;

}
