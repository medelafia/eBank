package com.accountservice.entities;


import com.accountservice.dto.AccountRequest;
import com.accountservice.enums.AccountStatus;
import com.accountservice.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Account {
    @Id
    private String accountId ;
    private String currency ;
    @Enumerated(EnumType.STRING)
    private AccountType accountType ;
    private BigDecimal balance ;
    private Date createdAt ;
    @Enumerated(EnumType.STRING)
    private AccountStatus status ;
    @JsonIgnore
    private String userId ;
    @Transient
    private User user ;

    public Account(AccountRequest accountRequest) {
        this.accountId = accountRequest.getAccountId() == null ? UUID.randomUUID().toString() : accountRequest.getAccountId();
        this.currency = accountRequest.getCurrency();
        this.accountType = accountRequest.getAccountType();
        this.balance = accountRequest.getBalance();
        this.status = accountRequest.getStatus();
        this.user = accountRequest.getUser();
    }
}
