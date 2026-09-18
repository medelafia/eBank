package com.transactionservice.service;

import com.transactionservice.dto.TransactionRequest;
import com.transactionservice.dto.TransactionResponse;
import com.transactionservice.entities.Transaction;
import com.transactionservice.events.TransactionCreatedEvent;
import com.transactionservice.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponse save(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction(transactionRequest);

        return transactionRepository.save(transaction).toTransactionResponse();
    }
    public List<TransactionResponse> getAccountTransactions(String accountId) {
        return this.transactionRepository
                .findAllByAccountId(accountId)
                .stream().map(Transaction::toTransactionResponse)
                .collect(Collectors.toList());
    }
    public List<TransactionResponse> getTodayTransactions() {
        return this.transactionRepository
                .findAllByDate(Date.valueOf(LocalDate.now()))
                .stream().map(Transaction::toTransactionResponse)
                .collect(Collectors.toList());
    }


    @KafkaListener(topics = "transactions-topic" , groupId = "transaction-group")
    @Transactional
    public void listenTransaction(TransactionCreatedEvent transactionCreatedEvent) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .date(transactionCreatedEvent.getTransactionDate())
                .amount(BigDecimal.valueOf(transactionCreatedEvent.getAmount()))
                .transactionType(transactionCreatedEvent.getTransactionType())
                .time(transactionCreatedEvent.getTransactionTime())
                .accountId(transactionCreatedEvent.getAccountId())
                .build();

        this.transactionRepository.save(transaction);
    }
}
