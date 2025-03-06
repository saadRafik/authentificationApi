package authCom.controllers;

import authCom.dto.ApiResponseDto;
import authCom.dto.TokenDto;
import authCom.dto.UserRequestDto;
import authCom.services.AuthenticationService;
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
        ResponseEntity<ApiResponseDto> response = authService.generateToken(credentials.getUsername(), credentials.getPassword());

        // Extract the token from the response body
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            String token = response.getBody().getToken();
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }


    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        String username = authService.validateToken(token);

        if (username.equals("Token expiré") || username.equals("Token invalide")) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized: Invalid or expired token"));
        }

        return ResponseEntity.ok(Map.of("username", username));
    }

}
