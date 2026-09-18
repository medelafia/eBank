package com.accountservice.exceptions;


import com.accountservice.dto.ErrorDetails;
import com.accountservice.dto.OperationResponse;
import com.accountservice.enums.TransactionStatus;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.Timestamp;
import java.time.Instant;


@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.ok(
                ErrorDetails.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(e.getMessage())
                        .timestamp(Timestamp.from(Instant.now()))
                        .build()
        ) ;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleAccountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.ok(
                ErrorDetails.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .timestamp(Timestamp.from(Instant.now()))
                        .build()
        ) ;
    }

    @ExceptionHandler(TransactionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleTransactionException(AccountNotFoundException e) {
        return ResponseEntity.ok(
                ErrorDetails.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .timestamp(Timestamp.from(Instant.now()))
                        .build()
        ) ;
    }
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorDetails> handleValidationException(ValidationException e) {
        return ResponseEntity.ok(
                ErrorDetails.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .timestamp(Timestamp.from(Instant.now()))
                        .build()
        ) ;
    }
}
