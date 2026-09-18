package com.transactionservice.entities;


import com.transactionservice.dto.TransactionRequest;
import com.transactionservice.dto.TransactionResponse;
import com.transactionservice.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Transaction {
    @Id
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private Date date;
    private Time time;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public Transaction(TransactionRequest transactionRequest) {
        this.transactionId = UUID.randomUUID().toString();
        this.accountId = transactionRequest.getAccountId();
        this.transactionType = transactionRequest.getTransactionType();
        this.amount = transactionRequest.getAmount();
        this.date = transactionRequest.getDate();
        this.time = transactionRequest.getTime();
    }
    public TransactionResponse toTransactionResponse() {
        return new TransactionResponse(transactionId, accountId, amount, date, time, transactionType);
    }
}
