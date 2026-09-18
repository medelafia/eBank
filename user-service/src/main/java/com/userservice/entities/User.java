package com.userservice.entities;


import com.userservice.dto.UserRequest;
import com.userservice.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    private String id ;
    private String firstName ; ;
    private String lastName ;
    private String email ;
    private String phoneNumber ;

    public User(UserRequest userRequest) {
        this.id = userRequest.getId() == null ? UUID.randomUUID().toString() : userRequest.getId() ;
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.email = userRequest.getEmail();
        this.phoneNumber = userRequest.getPhoneNumber();
    }
    public UserResponse toUserResponse() {
        return new UserResponse(
                id,
                firstName,
                lastName,
                email,
                phoneNumber
        );
    }
}
