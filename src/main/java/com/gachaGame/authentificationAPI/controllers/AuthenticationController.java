package com.gachaGame.authentificationAPI.controllers;

import com.gachaGame.authentificationAPI.domain.TokenDto;
import com.gachaGame.authentificationAPI.domain.UserRequestDto;
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
    public ResponseEntity<?> authenticate(@RequestBody UserRequestDto credentials) {
        String token = authService.generateToken(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody TokenDto requestData) {
        String username = authService.validateToken(requestData.getToken());
        return ResponseEntity.ok(Map.of("username", username));
    }
}
