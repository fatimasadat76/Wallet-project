package com.mousavi.the_java_academy_bank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false,unique = true,length = 10)
    private String nationalId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private Date dateOfBirth;
    @Column(unique = true,nullable = false,length = 12,updatable = true)
    private String phoneNumber;
    @Column(unique = true,nullable = false,length = 12,updatable = true)
    private String alternativePhoneNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false,unique = true,updatable = true)
    private String email;
    @Column(nullable = false)
    private String stateOfOrigin;
    @Column
    private String militaryStatus;
    @Column(unique = true,nullable = false,length = 8,updatable = false)
    private String accountNumber;
    @Column(unique = true,nullable = false,updatable = false)
    private String iban;
    @Column
    private BigDecimal accountBalance;
    @Column(unique = true,nullable = false,updatable = true)
    private String accountName;
    @Column(unique = true,nullable = false,updatable = true)
    private String password;
    @Column
    private String status;
    private Role role;
    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();



    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private BigDecimal dailyWithdrawnAmount;
    private LocalDate lastWithdrawalDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public String getPassword(){
        return password ;
    }

    public BigDecimal getDailyWithdrawnAmount() {
        return dailyWithdrawnAmount;
    }

    public void setDailyWithdrawnAmount(BigDecimal dailyWithdrawnAmount) {
        this.dailyWithdrawnAmount = dailyWithdrawnAmount;
    }
    public void resetDailyWithdrawnAmountIfNewDay() {
        if (lastWithdrawalDate == null || !lastWithdrawalDate.isEqual(LocalDate.now())) {
            dailyWithdrawnAmount = BigDecimal.ZERO;
            lastWithdrawalDate = LocalDate.now();
        }
    }

    /**public int getAge() {
        Calendar today = Calendar.getInstance();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTime(this.dateOfBirth);
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
*/       /** private void militaryStatus(){
        if(gender.equalsIgnoreCase("Male")){
            int age = Period.between(dateOfBirth,Date.now()).getYears();
            if (age >=18){
                return "Please specify military status(served,exempted):" ;
            }
        }
    }*/


}
