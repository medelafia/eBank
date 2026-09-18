package com.transactionservice.dto;

import com.transactionservice.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public record TransactionResponse(
         String transactionId,
         String accountId,
         BigDecimal amount,
         Date date,
         Time time,
         TransactionType transactionType
) {
}
