package com.accountservice.controller;

import com.accountservice.dto.AccountRequest;
import com.accountservice.dto.AccountResponse;
import com.accountservice.dto.OperationRequest;
import com.accountservice.dto.OperationResponse;
import com.accountservice.services.AccountServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin({"http://localhost:4200"})
public class AccountController {
    private final AccountServices accountServices;
    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccount() {
        return ResponseEntity.ok(this.accountServices.getAccounts());
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(this.accountServices.getAccountById(accountId));
    }
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountServices.createAccount(account));
    }
    @PutMapping
    public ResponseEntity<AccountResponse> updateAccount(@Valid @RequestBody AccountRequest account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountServices.updateAccount(account));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable String accountId) {
        this.accountServices.deleteAccount(accountId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<OperationResponse> withdrawAccount(@PathVariable String accountId , @RequestBody OperationRequest operationRequest) {
        return ResponseEntity.ok(this.accountServices.withdraw(accountId , operationRequest)) ;
    }
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<OperationResponse> deposit(@PathVariable String accountId , @RequestBody OperationRequest operationRequest) {
        return ResponseEntity.ok(this.accountServices.deposit(accountId , operationRequest)) ;
    }
    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<OperationResponse> transfer(@PathVariable String accountId , @RequestBody OperationRequest operationRequest) {
        return ResponseEntity.ok(this.accountServices.transfer(accountId , operationRequest)) ;
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(this.accountServices.getAllAccountsByUserId(userId)) ;
    }
}
