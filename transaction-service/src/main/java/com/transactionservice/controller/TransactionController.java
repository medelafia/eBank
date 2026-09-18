package com.transactionservice.controller;


import com.transactionservice.dto.TransactionRequest;
import com.transactionservice.dto.TransactionResponse;
import com.transactionservice.entities.Transaction;
import com.transactionservice.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@CrossOrigin({"http://localhost:4200"})
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transaction) {
        TransactionResponse savedTransaction = transactionService.save(transaction);
        return ResponseEntity.ok(savedTransaction);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getAccountsTransaction(@PathVariable String accountId) {
        return ResponseEntity.ok(this.transactionService.getAccountTransactions(accountId));
    }
    @GetMapping("/recents")
    public ResponseEntity<List<TransactionResponse>> getRecentTransactions() {
        return ResponseEntity.ok(this.transactionService.getTodayTransactions()) ;
    }
}
