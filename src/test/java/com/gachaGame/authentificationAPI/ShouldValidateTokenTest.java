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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShouldValidateTokenTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldValidateToken() throws Exception {
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("should_validate_token.json");
        ObjectMapper mapper = new ObjectMapper();
        Token token = mapper.readValue(jsonStream, Token.class);

        when(tokenRepository.findByToken(token.getToken()))
                .thenReturn(Optional.of(token));

        String result = authenticationService.validateToken(token.getToken());

        assertEquals(token.getUsername(), result);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }
}
