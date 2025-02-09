package com.gachaGame.authentificationAPI.services;

import com.gachaGame.authentificationAPI.dataAccess.*;
import com.gachaGame.authentificationAPI.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public AuthenticationService(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public String generateToken(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()
                && userOpt.get().getPassword().equals(password)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss"));
            String tokenValue = username + "-" + timestamp + "-" + UUID.randomUUID();

            Token token = new Token();
            token.setToken(tokenValue);
            token.setUsername(username);
            token.setExpirationDate(LocalDateTime.now().plusHours(1));

            tokenRepository.save(token);
            return tokenValue;
        }
        return "Identifiants incorrects";
    }

    public String validateToken(String token) {
        Optional<Token> tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isPresent()) {
            Token existingToken = tokenOpt.get();
            if (existingToken.getExpirationDate().isAfter(LocalDateTime.now())) {
                existingToken.setExpirationDate(LocalDateTime.now().plusHours(1));
                tokenRepository.save(existingToken);
                return existingToken.getUsername();
            } else {
                return "Token expiré";
            }
        }
        return "Token invalide";
    }
}
