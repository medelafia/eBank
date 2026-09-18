package com.userservice.dto;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {

}
