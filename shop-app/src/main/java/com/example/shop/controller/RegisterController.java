package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static class RegisterRequest {
        public String username;
        public String password;
        public String fullName;
        public String email;
        public String address;
        public String phone;
        public String accountDetails;
    }

    @PostMapping("/api/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        if (request.username == null || request.username.isBlank() || request.password == null || request.password.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Username and password are required."));
        }
        if (userRepository.findByUsername(request.username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("success", false, "message", "Username already exists."));
        }

        User user = new User(request.username, request.password, "customer", request.fullName, request.email, request.address, request.phone, request.accountDetails, false);
        User saved = userRepository.save(user);

        return ResponseEntity.ok(Map.of("success", true, "user", Map.of(
                "username", saved.getUsername(),
                "fullName", saved.getFullName(),
                "email", saved.getEmail(),
                "role", saved.getRole(),
                "address", saved.getAddress(),
                "phone", saved.getPhone(),
                "accountDetails", saved.getAccountDetails()
        )));
    }
}
