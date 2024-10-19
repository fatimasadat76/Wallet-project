package com.mousavi.the_java_academy_bank.repository;

import com.mousavi.the_java_academy_bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long>{


    Boolean existsBynNationalId(String NationalId);


    boolean existsByAccountNumber(String accountNumber);

    User findByAccountNumber(String accountNumber);
    User findByIban(String iban);

    boolean existsByIban(String sourceIban);

    UserDetails findByEmail();
    Optional<User> findByEmail(String email);
}
