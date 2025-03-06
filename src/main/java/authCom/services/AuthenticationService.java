package authCom.services;

import authCom.dataAccess.TokenRepository;
import authCom.dataAccess.UserRepository;
import authCom.domain.Token;
import authCom.domain.User;
import authCom.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<ApiResponseDto> generateToken(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss"));
            String tokenValue = username + "-" + timestamp + "-" + UUID.randomUUID();

            Token token = new Token();
            token.setToken(tokenValue);
            token.setUsername(username);
            token.setExpirationDate(LocalDateTime.now().plusHours(1));

            tokenRepository.save(token);

            return ResponseEntity.ok(new ApiResponseDto("Token created successfully", 200));
        }
        return ResponseEntity.status(401).body(new ApiResponseDto("Identifiants incorrects", 401));
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
                tokenRepository.delete(existingToken); // Remove expired token
                return "Token expiré";
            }
        }
        return "Token invalide";
    }
}
