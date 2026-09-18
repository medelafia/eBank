package com.userservice.controller;


import com.userservice.dto.UserRequest;
import com.userservice.dto.UserResponse;
import com.userservice.entities.User;
import com.userservice.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {
    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(defaultValue = "0" , required = false) int page, @RequestParam(defaultValue = "5" , required = false) int size) {
        return ResponseEntity.ok(this.userServices.getAllUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userServices.createUser(user));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId ) {
        return ResponseEntity.ok(this.userServices.findUserById(userId)) ;
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user) {
        return ResponseEntity.ok(this.userServices.updateUser(user));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        this.userServices.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
