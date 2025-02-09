package com.gachaGame.authentificationAPI.controllers;

import com.gachaGame.authentificationAPI.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> credentials) {
        String token = authService.generateToken(credentials.get("username"), credentials.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> requestData) {
        String username = authService.validateToken(requestData.get("token"));
        return ResponseEntity.ok(Map.of("username", username));
    }
}
