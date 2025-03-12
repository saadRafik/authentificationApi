package com.gachaGame.authentificationAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachaGame.authentificationAPI.dataAccess.TokenRepository;
import com.gachaGame.authentificationAPI.dataAccess.UserRepository;
import com.gachaGame.authentificationAPI.domain.User;
import com.gachaGame.authentificationAPI.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShouldDeleteUserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldDeleteUser() throws Exception {
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("should_delete_user.json");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonStream, User.class);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        String result = userService.deleteUser(user.getUsername(), user.getPassword());

        assertEquals(String.format("Utilisateur %s supprimé", user.getUsername()), result);
        verify(tokenRepository, times(1)).deleteByUsername(user.getUsername());
        verify(userRepository, times(1)).delete(user);
    }
}
