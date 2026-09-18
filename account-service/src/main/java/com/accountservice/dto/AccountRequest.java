package com.accountservice.dto;

import com.accountservice.entities.User;
import com.accountservice.enums.AccountStatus;
import com.accountservice.enums.AccountType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AccountRequest {
    private String accountId;
    @NotBlank(message = "Currency is required")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency must be a 3-letter ISO 4217 code (e.g. USD, EUR, MAD)"
    )
    private String currency;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    @DecimalMax(value = "999999999999.99", message = "Balance exceeds the maximum allowed")
    @Digits(integer = 12, fraction = 2, message = "Balance must have at most 12 digits and 2 decimals")
    private BigDecimal balance;

    @NotNull(message = "Account status is required")
    private AccountStatus status;

    @NotNull(message = "User is required")
    @Valid
    private User user;
}