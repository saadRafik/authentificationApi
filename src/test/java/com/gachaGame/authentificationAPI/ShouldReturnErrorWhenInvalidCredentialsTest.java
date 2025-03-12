package com.gachaGame.authentificationAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachaGame.authentificationAPI.dataAccess.TokenRepository;
import com.gachaGame.authentificationAPI.dataAccess.UserRepository;
import com.gachaGame.authentificationAPI.domain.User;
import com.gachaGame.authentificationAPI.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Should_Return_Error_When_Invalid_Credentials {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldReturnErrorWhenInvalidCredentials() throws Exception {
        // Chargement du JSON de test
        InputStream jsonStream = getClass().getClassLoader()
                .getResourceAsStream("should_return_error_when_invalid_credentials.json");
        ObjectMapper mapper = new ObjectMapper();
        User testUser = mapper.readValue(jsonStream, User.class);

        // Mocking repository : Utilisateur non trouvé
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.empty());

        String result = authenticationService.generateToken(testUser.getUsername(), testUser.getPassword());

        assertEquals("Identifiants incorrects", result);
    }
}
