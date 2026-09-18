package com.accountservice.services;

import com.accountservice.dto.*;
import com.accountservice.entities.Account;
import com.accountservice.entities.User;
import com.accountservice.enums.NotificationEventType;
import com.accountservice.enums.TransactionStatus;
import com.accountservice.enums.TransactionType;
import com.accountservice.events.TransactionCreatedEvent;
import com.accountservice.exceptions.AccountNotFoundException;
import com.accountservice.exceptions.TransactionException;
import com.accountservice.repositories.AccountRepository;
import com.accountservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServices {
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplateTransaction;
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplateNotification;
    private static final String TRANSACTION_TOPIC = "transactions-topic";
    private static final String NOTIFICATION_TOPIC = "notifications-topic";
    private final UserRepository  userRepository;

    public AccountServices(AccountRepository accountRepository , KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplateTransaction , KafkaTemplate<String, NotificationEvent> kafkaTemplateNotification , UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.kafkaTemplateTransaction = kafkaTemplateTransaction;
        this.kafkaTemplateNotification = kafkaTemplateNotification;
        this.userRepository = userRepository;
    }

    public List<AccountResponse> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        accounts.forEach(account -> account.setUser(this.userRepository.findById(account.getUserId()).getBody()));

        return accounts.stream().map(AccountResponse::from).collect(Collectors.toList()) ;
    }
    public AccountResponse getAccountById(String accountId) {
        Account account = this.accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " doesn't exist")) ;
        account.setUser(this.userRepository.findById(account.getUserId()).getBody());
        return AccountResponse.from(account);
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest);
        ResponseEntity<User> userResponseEntity = this.userRepository.findById(account.getUser().getId());

        if(userResponseEntity.getStatusCode().isError()) {
            throw new AccountNotFoundException("User with id " + account.getUser().getId() + " doesn't exist");
        }
        account.setUserId(userResponseEntity.getBody().getId());
        account.setCreatedAt(Date.valueOf(LocalDate.now()));
        account.setUser(userResponseEntity.getBody());

        Account savedAccount = this.accountRepository.save(account);
        savedAccount.setUser(userResponseEntity.getBody());

        this.kafkaTemplateNotification.send(
                NOTIFICATION_TOPIC ,
                NotificationEvent.builder()
                        .eventId(UUID.randomUUID().toString())
                        .message("Account created")
                        .notificationEventType(NotificationEventType.ACCOUNT_CREATED_EVENT)
                        .timestamp(Timestamp.from(Instant.now()))
                        .email(account.getUser().getEmail())
                        .build()
        );

        return AccountResponse.from(savedAccount);
    }
    @Transactional
    public AccountResponse updateAccount(AccountRequest accountRequest) {
        Optional<Account> accountOptional = this.accountRepository.findById(accountRequest.getAccountId());
        if(accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account with id " + accountRequest.getAccountId() + " doesn't exist");
        }

        Account account = accountOptional.get();
        ResponseEntity<User> userResponseEntity = this.userRepository.findById(account.getUserId());

        if(userResponseEntity.getStatusCode().isError()) {
            throw new AccountNotFoundException("User with id " + account.getUserId() + " doesn't exist");
        }

        account.setAccountType(accountRequest.getAccountType());
        account.setStatus(accountRequest.getStatus());

        Account savedAccount = this.accountRepository.save(account);
        savedAccount.setUser(userResponseEntity.getBody());

        this.kafkaTemplateNotification.send(
                NOTIFICATION_TOPIC ,
                NotificationEvent.builder()
                        .eventId(UUID.randomUUID().toString())
                        .message("Account updated")
                        .notificationEventType(NotificationEventType.ACCOUNT_CREATED_EVENT)
                        .timestamp(Timestamp.from(Instant.now()))
                        .email(savedAccount.getUser().getEmail())
                        .build()
        );

        return AccountResponse.from(savedAccount);
    }

    @Transactional
    public void deleteAccount(String accountId) {
        Optional<Account> account = this.accountRepository.findById(accountId) ;
        if(!account.isPresent()) {
            throw new AccountNotFoundException("Account with id " + accountId + " does not exist");
        }

        User user = this.userRepository.findById(account.get().getUserId()).getBody();

        this.kafkaTemplateNotification.send(
                NOTIFICATION_TOPIC ,
                NotificationEvent.builder()
                        .eventId(UUID.randomUUID().toString())
                        .message("Account "+ accountId + " deleted")
                        .notificationEventType(NotificationEventType.ACCOUNT_DELETED_EVENT)
                        .timestamp(Timestamp.from(Instant.now()))
                        .email(user.getEmail())
                        .build()
        );
        accountRepository.deleteById(accountId);
    }

    @Transactional
    public OperationResponse withdraw(String accountId ,OperationRequest operationRequest) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
       if(operationRequest.getAmount().compareTo(account.getBalance()) > 0) {
            throw new TransactionException("Amount not enough");
        }

        account.setBalance(account.getBalance().subtract(operationRequest.getAmount()));
        accountRepository.save(account) ;

        this.kafkaTemplateTransaction.send(TRANSACTION_TOPIC , TransactionCreatedEvent.builder()
                .accountId(accountId)
                .amount(operationRequest.getAmount().doubleValue())
                .transactionDate(Date.valueOf(LocalDate.now()))
                .transactionTime(Time.valueOf(LocalTime.now()))
                .transactionType(TransactionType.WITHDRAW)
                .build()) ;
        this.kafkaTemplateNotification.send(
                NOTIFICATION_TOPIC ,
                NotificationEvent.builder()
                        .eventId(UUID.randomUUID().toString())
                        .message("New Transaction Success , Transaction type : " + TransactionType.WITHDRAW.toString() + " , amount : " + operationRequest.getAmount().doubleValue())
                        .notificationEventType(NotificationEventType.TRANSACTION_EVENT)
                        .timestamp(Timestamp.from(Instant.now()))
                        .email(this.userRepository.findById(account.getUserId()).getBody().getEmail())
                        .build()
        );

        return OperationResponse.builder()
                .status(TransactionStatus.SUCCESS)
                .TransactionType(TransactionType.WITHDRAW)
                .build() ;
    }

    @Transactional
    public OperationResponse deposit(String accountId ,OperationRequest operationRequest ) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));

        if (operationRequest.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionException("Cannot deposit negative amount");
        }
        account.setBalance(account.getBalance().add(operationRequest.getAmount()));

        accountRepository.save(account) ;

        this.kafkaTemplateTransaction.send(TRANSACTION_TOPIC , TransactionCreatedEvent.builder()
                .accountId(accountId)
                .amount(operationRequest.getAmount().doubleValue())
                .transactionDate(Date.valueOf(LocalDate.now()))
                .transactionTime(Time.valueOf(LocalTime.now()))
                .transactionType(TransactionType.DEPOSIT)
                .build()) ;
        this.kafkaTemplateNotification.send(
                NOTIFICATION_TOPIC ,
                NotificationEvent.builder()
                        .eventId(UUID.randomUUID().toString())
                        .message("New Transaction Success , Transaction type : " + TransactionType.WITHDRAW.toString() + " ,amout : " + operationRequest.getAmount().doubleValue())
                        .notificationEventType(NotificationEventType.TRANSACTION_EVENT)
                        .timestamp(Timestamp.from(Instant.now()))
                        .email(this.userRepository.findById(account.getUserId()).getBody().getEmail())
                        .build()
        );
        return OperationResponse.builder()
                .status(TransactionStatus.SUCCESS)
                .TransactionType(TransactionType.DEPOSIT)
                .build();
    }

    @Transactional
    public OperationResponse transfer(String accountId , OperationRequest operationRequest) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        Account destAccount = accountRepository.findById(operationRequest.getDestAccountId()).orElseThrow(() -> new AccountNotFoundException("Account " + operationRequest.getDestAccountId() + " not found"));

        if(operationRequest.getAmount().compareTo(account.getBalance()) > 0) {
            throw new TransactionException("Amount not enough");
        }
        account.setBalance(account.getBalance().subtract(operationRequest.getAmount()));
        destAccount.setBalance(destAccount.getBalance().add(operationRequest.getAmount()));

        this.accountRepository.save(account);
        this.accountRepository.save(destAccount);

        this.kafkaTemplateTransaction.send(TRANSACTION_TOPIC , TransactionCreatedEvent.builder()
                .accountId(accountId)
                .amount(operationRequest.getAmount().doubleValue())
                .transactionDate(Date.valueOf(LocalDate.now()))
                .transactionTime(Time.valueOf(LocalTime.now()))
                .transactionType(TransactionType.TRANSFER)
                .build()) ;
        this.kafkaTemplateNotification.send(
                NOTIFICATION_TOPIC ,
                NotificationEvent.builder()
                        .eventId(UUID.randomUUID().toString())
                        .message("New Transaction Success , Transaction type : " + TransactionType.TRANSFER.toString() + " ,amout : " + operationRequest.getAmount().doubleValue())
                        .notificationEventType(NotificationEventType.TRANSACTION_EVENT)
                        .timestamp(Timestamp.from(Instant.now()))
                        .email(this.userRepository.findById(account.getAccountId()).getBody().getEmail())
                        .build()
        );

        return OperationResponse.builder()
                .status(TransactionStatus.SUCCESS)
                .TransactionType(TransactionType.TRANSFER)
                .build();
    }

    public List<AccountResponse> getAllAccountsByUserId(String userId) {
        return this.accountRepository.findAllByUserId(userId).stream().map(AccountResponse::from).collect(Collectors.toList());
    }
}
