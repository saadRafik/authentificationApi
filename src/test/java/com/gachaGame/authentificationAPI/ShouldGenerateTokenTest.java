package com.gachaGame.authentificationAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachaGame.authentificationAPI.dataAccess.TokenRepository;
import com.gachaGame.authentificationAPI.dataAccess.UserRepository;
import com.gachaGame.authentificationAPI.domain.Token;
import com.gachaGame.authentificationAPI.domain.User;
import com.gachaGame.authentificationAPI.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShouldGenerateTokenTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldGenerateToken() throws Exception {
        // Chargement du JSON de test
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("should_generate_token.json");
        ObjectMapper mapper = new ObjectMapper();
        User testUser = mapper.readValue(jsonStream, User.class);

        // Mocking comportement repository
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        String token = authenticationService.generateToken(testUser.getUsername(), testUser.getPassword());

        assertNotNull(token);
        assertNotEquals("Identifiants incorrects", token);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }
}

