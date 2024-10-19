package com.mousavi.the_java_academy_bank.dto;

import com.mousavi.the_java_academy_bank.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserRequest {

    private String nationalId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private String gender;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;
    private String militaryStatus;
    private String stateOfOrigin;
    private String accountName;
    private String iban;
    private String password;
    private String responseCode;
    private String responseMassage;
    private Account account;

    public int getAge() {
        Calendar today = Calendar.getInstance();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTime(this.dateOfBirth);
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }
}
