package com.mousavi.the_java_academy_bank.repository;
import com.mousavi.the_java_academy_bank.entity.Account;
import com.mousavi.the_java_academy_bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Long,Account>{


        static Object save(Account account) {
                return null;
        }

        static <T> ScopedValue findById(Long accountId) {
                return null;
        }

        static void delete(Account account) {

        }

        boolean existsByAccountNumber(String accountNumber);

        Account findByAccountNumber(String accountNumber);
        Account findByIban(String iban);
        boolean existsByIban(String sourceIban);

        Account findByEmail();
        Optional<Account> findByEmail(String email);


        }
