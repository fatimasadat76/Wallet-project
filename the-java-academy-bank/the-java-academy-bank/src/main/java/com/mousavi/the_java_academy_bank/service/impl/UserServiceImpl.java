package com.mousavi.the_java_academy_bank.service.impl;

import com.mousavi.the_java_academy_bank.config.JwtTokenProvider;
import com.mousavi.the_java_academy_bank.dto.*;
import com.mousavi.the_java_academy_bank.entity.*;
import com.mousavi.the_java_academy_bank.exception.ResourceNotFoundException;
import com.mousavi.the_java_academy_bank.repository.UserRepository;
import com.mousavi.the_java_academy_bank.utils.AccountUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        /**
         * Creating an account -saving a new user into the db
         * check if user already an account
         */
        if (userRepository.existsBynNationalId(userRequest.getNationalId())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMassage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .account(null)
                    .build();

        }

        if(userRequest.getGender().equals("male") && userRequest.getAge() > 18){
            if(userRequest.getMilitaryStatus() == null || userRequest.getMilitaryStatus().isEmpty()){
                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_FILL_CODE)
                        .responseMassage(AccountUtils.ACCOUNT_MILITARY_STATUS)
                        .build();
            }
        }


        User.UserBuilder builder = User.builder();
        builder.nationalId(userRequest.getNationalId());
        builder.firstName(userRequest.getFirstName());
        builder.lastName(userRequest.getLastName());
        builder.gender(Gender.valueOf(String.valueOf(userRequest.getGender())));
        builder.dateOfBirth(userRequest.getDateOfBirth());
        builder.phoneNumber(userRequest.getPhoneNumber());
        builder.alternativePhoneNumber(userRequest.getAlternativePhoneNumber());
        builder.address(userRequest.getAddress());
        builder.email(userRequest.getEmail());
        builder.stateOfOrigin(userRequest.getStateOfOrigin());
        builder.militaryStatus(MilitaryStatus.valueOf(String.valueOf(userRequest.getMilitaryStatus())));
        builder.accountNumber(AccountUtils.generateAccountNumber());
        builder.accountBalance(BigDecimal.ZERO);
        builder.iban(AccountUtils.generateAccountIban(AccountUtils.generateAccountNumber()));
        builder.password(passwordEncoder.encode(userRequest.getPassword()));
        builder.status("Active");
        builder.role(Role.valueOf("ROLE_ADMIN"));
        User newUser = builder
                .build();


        User saveUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveUser.getEmail())
                .subjects("Account creation")
                .messageBody("Congratulations! Your Account Has Successfully Created.\n Your Account Details : \n" +
                        "Account Name: " + saveUser.getFirstName() + " " + saveUser.getLastName() + "\n Account Number:"+ saveUser.getAccountNumber()
                       + "\n Account iban :"+ saveUser.getIban() + "\n Account Name :" + saveUser.getAccountName())
                .build();
        emailService.sendEmailAllert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMassage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .account(Account.builder()
                        .accountBalance(saveUser.getAccountBalance())
                        .accountNumber(saveUser.getAccountNumber())
                        .iban(saveUser.getIban())
                        .accountId(saveUser.getFirstName() + " " + saveUser.getLastName() )
                        .build())
                .build();

    }
    public User updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setNationalId(userRequest.getNationalId());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setGender(Gender.valueOf(String.valueOf(userRequest.getGender())));
        user.setDateOfBirth();
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAlternativePhoneNumber(userRequest.getAlternativePhoneNumber());
        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());
        user.setStateOfOrigin(userRequest.getStateOfOrigin());
        user.setMilitaryStatus(MilitaryStatus.valueOf(String.valueOf(userRequest.getMilitaryStatus())));

        return  userRepository.save(user);
    }
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public BankResponse login(LoginDto loginDto) {
        Authentication authentication=null;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        EmailDetails loginAlert = EmailDetails.builder()
                .subjects("You are logged in")
                .recipient(loginDto.getEmail())
                .messageBody("You logged into your account.if you did not initiate this request,please contact your bank")
                .build();
        emailService.sendEmailAllert(loginAlert);
        return BankResponse.builder()
                .responseCode("Login Success")
                .responseMassage(jwtTokenProvider.generateToken(authentication))
                .build();


    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean isAccountExist =  userRepository.existsByAccountNumber(request.getAccountNumber());
        if(! isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMassage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .account(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return  BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMassage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .account(Account.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .iban(request.getIban())
                        .accountName(foundUser.getFirstName() + " "+ foundUser.getLastName() )
                        .build())
                .build();

    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {

            boolean isAccountExist =  userRepository.existsByAccountNumber(request.getAccountNumber());
            if(! isAccountExist){
                return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
            }
            User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
            return foundUser.getFirstName() + " " + foundUser.getLastName();

    }


    //balance Enquiry,name Enquiry ,debit ,transfer

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean isAccountExist =  userRepository.existsByAccountNumber(request.getAccountNumber());
        if(! isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMassage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .account(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        userRepository.save(userToCredit);

        //Save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType("Credit")
                .amount(request.getAmount())
                .build();
        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATED_SUCCESS)
                .responseMassage(AccountUtils.ACCOUNT_CREATED_SUCCESS_MESSAGE)
                .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                .iban(userToCredit.getIban())
                .accountBalance(userToCredit.getAccountBalance())
                .accountNumber(request.getAccountNumber())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        boolean isAccountExist =  userRepository.existsByAccountNumber(request.getAccountNumber());
        if(! isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMassage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .account(null)
                    .build();
    }
        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        BigDecimal availableBalance = BigDecimal.valueOf(Long.parseLong(userToDebit.getAccountBalance().toString()));
        BigDecimal debitAmount = BigDecimal.valueOf(Long.parseLong(request.getAmount().toString()));

            if(availableBalance.longValue() < debitAmount.longValue()){
                return BankResponse.builder()
                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                        .responseMassage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                        .account(null)
                        .build();
            }

            else {
                userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
                userRepository.save(userToDebit);
            TransactionDto transactionDto = TransactionDto.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .transactionType("Credit")
                    .amount(request.getAmount())
                    .build();
            transactionService.saveTransaction(transactionDto);

                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                        .responseMassage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                        .account(Account.builder()
                                .accountNumber(request.getAccountNumber())
                                .iban(request.getIban())
                                .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() )
                                .accountBalance(userToDebit.getAccountBalance())

                                .build())
                        .build();
            }
        }

    @Override
    public BankResponse transfer(TransferRequest request) {
        //get the account to debit
        //check if the amount i m debiting is not than the current balance
        //debit the account
        //get the account to credit
        // the account
        boolean isSourceAccountExist = ((userRepository.existsByAccountNumber(request.getSourceAccountNumber())) && (userRepository.existsByIban(request.getSourceIban())));
        boolean isDestinationAccountExist=((userRepository.existsByAccountNumber(request.getDestinationAccountNumber()))&& (userRepository.existsByIban(request.getDestinationIban())));
        if(!isDestinationAccountExist ){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMassage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .account(null)
                    .build();
        }

       User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        BigDecimal requestAmount = request.getAmount();
        BigDecimal currentBalance = sourceAccountUser.getAccountBalance();
        BigDecimal dailyWithdrawn = sourceAccountUser.getDailyWithdrawnAmount();
        sourceAccountUser.resetDailyWithdrawnAmountIfNewDay();

        if (dailyWithdrawn.add(requestAmount).compareTo(new BigDecimal("10000000")) > 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.DAILY_WITHDRAWAL_LIMIT_CODE)
                    .responseMassage(AccountUtils.DAILY_WITHDRAWAL_LIMIT_MESSAGE)
                    .account(null).build();
        }

        if (10000<(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()))&&(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) <10000000)){
      return BankResponse.builder()
              .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
              .responseMassage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
              .account(null).build();
        }
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
        String sourceUsername = sourceAccountUser.getFirstName() +" "+ sourceAccountUser.getLastName();

        userRepository.save(sourceAccountUser);
        EmailDetails debitAlert =EmailDetails.builder()
            .subjects("DEBIT ALERT")
            .recipient(sourceAccountUser.getEmail())
            .messageBody("the sum of " + request.getAmount() + " has been deducted from your account!Your current balance is" + sourceAccountUser.getAccountBalance())
            .build();

        emailService.sendEmailAllert(debitAlert);

        User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
        String recipientUsername = destinationAccountUser.getFirstName() + " " + destinationAccountUser.getLastName();
        userRepository.save(destinationAccountUser);
        EmailDetails creditAlert =EmailDetails.builder()
            .subjects("CREDIT ALERT")
            .recipient(sourceAccountUser.getEmail())
            .messageBody("the sum of " + request.getAmount() + " has been sent to your account from" + sourceUsername + "your current balance " + sourceAccountUser.getAccountBalance())
            .build();

        emailService.sendEmailAllert(creditAlert);

        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(destinationAccountUser.getAccountNumber())
                .transactionType("Credit")
                .amount(request.getAmount())
                .build();
        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMassage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .account(null)
                .build();
    }
}