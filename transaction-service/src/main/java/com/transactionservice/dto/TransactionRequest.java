package com.transactionservice.dto;


import com.transactionservice.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequest {

    private String accountId;
    private BigDecimal amount;
    private Date date;
    private Time time;
    private TransactionType transactionType;
}
