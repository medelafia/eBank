package com.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {
    private String id;
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}'\\- ]*$",
            message = "First name can only contain letters, spaces, hyphens and apostrophes"
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}'\\- ]*$",
            message = "Last name can only contain letters, spaces, hyphens and apostrophes"
    )
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid address")
    @Size(max = 254, message = "Email must not exceed 254 characters")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number must contain 7 to 15 digits, optionally starting with +"
    )
    private String phoneNumber;
}