package com.mousavi.the_java_academy_bank.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(unique = true,nullable = false,length = 10)
    private String accountId;
    @Schema(
            name = "User Account Name"
    )
    @Column(nullable = false)
    private String accountName;

    @Schema(
            name = "User Account Number"
    )
    @Column(unique = true,nullable = false,updatable = false)
    private String accountNumber;
   @Schema(
           name = "User Account Iban"
   )
   @Column(unique = true,nullable = false,updatable = false)
   private String iban;

    @Schema(
            name = "User Account Balance"
    )
    @Column(nullable = false,updatable = true)
    private BigDecimal accountBalance;


    @Schema(
            name = "User Account Email"
    )
    @Column(nullable = false,unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "accountNumber" ,nullable = false)
    private User user;




    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;


}
