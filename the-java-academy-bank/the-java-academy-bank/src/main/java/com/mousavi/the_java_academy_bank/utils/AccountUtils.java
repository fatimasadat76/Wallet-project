package com.mousavi.the_java_academy_bank.utils;

import java.time.Year;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AccountUtils {
    public  static final String ACCOUNT_FILL_CODE ="000";
    public static final String ACCOUNT_MILITARY_STATUS ="Please enter your military-status";
    public static final String ACCOUNT_EXISTS_CODE ="001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "this user already exists";
    public static final String ACCOUNT_CREATION_SUCCESS ="002";
    public static final String ACCOUNT_CREATION_MESSAGE="Account has been successfully created";
    public static final String ACCOUNT_NOT_EXIST_CODE ="003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE ="User not found";
    public static final String ACCOUNT_FOUND_CODE="004";
    public static final String ACCOUNT_FOUND_MESSAGE= "User founded ";
    public static final String ACCOUNT_CREATED_SUCCESS ="005";
    public static final String ACCOUNT_CREATED_SUCCESS_MESSAGE="User account created";
    public static final String INSUFFICIENT_BALANCE_CODE = "006 ";
    public static final String INSUFFICIENT_BALANCE_MESSAGE= "insufficient Balance";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = " Account has been successfully debited";
    public static final String TRANSFER_SUCCESSFUL_CODE= "008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE="Transfer Successful";
    public static final String DAILY_WITHDRAWAL_LIMIT_CODE="009";
    public static final String DAILY_WITHDRAWAL_LIMIT_MESSAGE="Account cant withdraw";






    Set<String> existingAccounts;
    public AccountUtils(){
        existingAccounts =new HashSet<>();
    }

    /**
     * 2024 + random4Digits
     */
    public static String generateAccountNumber() {
        /**
        Year currentYear = Year.now();

        int min = 1000;
        int max = 9999;

        //genrate a random number between min and max
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        //convert the current and randomNumber to String then Concat
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        return year + randomNumber;
        */
        int year =java.util.Calendar.getInstance().get(Calendar.YEAR);
        Random random =new Random();
        int randomDigits =random.nextInt(9000)+1000;
        return String.valueOf(year) + String.valueOf(randomDigits);
    }

    /**
     *IR + random + accountNumber
     */
    public static String generateAccountIban(String generateAccountNumber){
        StringBuilder sb =new StringBuilder("IR");
        Random random =new Random();
        for(int i = 0; i<8 ;i++){
            sb.append(random.nextInt(10));
        }
        sb.append(generateAccountNumber);
        return sb.toString();
    }
}
