package com.userservice.services;


import com.userservice.dto.UserRequest;
import com.userservice.dto.UserResponse;
import com.userservice.entities.NotificationEvent;
import com.userservice.entities.User;
import com.userservice.enums.NotificationEventType;
import com.userservice.events.UserCreationEvent;
import com.userservice.exceptions.UserNotFoundException;
import com.userservice.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;
    private final static String TOPIC = "notifications-topic";

    public UserServices(UserRepository userRepository, KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    public List<UserResponse> getAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(User::toUserResponse)
                .collect(Collectors.toList());
    }
    public UserResponse findUserById(String userId) {
        System.out.println(userId);
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")).toUserResponse();
    }
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User(userRequest);
        user.setId(UUID.randomUUID().toString());
        this.kafkaTemplate.send(
                TOPIC ,
                NotificationEvent.builder()
                        .eventId(UUID.randomUUID().toString())
                        .message("Welcome , The User created successfully , your id : "+ user.getId())
                        .notificationEventType(NotificationEventType.USER_CREATED_EVENT)
                        .timestamp(Timestamp.from(Instant.now()))
                        .email(user.getEmail())
                        .build()
                ) ;

        return userRepository.save(user).toUserResponse();
    }
    public UserResponse updateUser(UserRequest userRequest) {
        User user = new User(userRequest);
        return userRepository.save(user).toUserResponse();
    }
    public void deleteUser(String id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

}
