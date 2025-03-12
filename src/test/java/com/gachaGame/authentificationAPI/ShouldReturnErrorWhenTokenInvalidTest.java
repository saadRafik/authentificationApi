package com.gachaGame.authentificationAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachaGame.authentificationAPI.dataAccess.TokenRepository;
import com.gachaGame.authentificationAPI.domain.Token;
import com.gachaGame.authentificationAPI.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShouldReturnErrorWhenTokenInvalidTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldReturnErrorWhenTokenInvalid() throws Exception {
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("should_return_error_when_token_invalid.json");
        ObjectMapper mapper = new ObjectMapper();
        Token expiredToken = mapper.readValue(jsonStream, Token.class);

        when(tokenRepository.findByToken(expiredToken.getToken()))
                .thenReturn(Optional.of(expiredToken));

        String result = authenticationService.validateToken(expiredToken.getToken());

        assertEquals("Token expiré", result);
    }
}
