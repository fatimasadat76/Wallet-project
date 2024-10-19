package com.mousavi.the_java_academy_bank.service.impl;

import com.mousavi.the_java_academy_bank.dto.*;
import com.mousavi.the_java_academy_bank.entity.User;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    void deleteUser(Long userId);
    User updateUser(Long userId, UserRequest userRequest);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);
    BankResponse login (LoginDto loginDto);



}
