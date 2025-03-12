package com.gachaGame.authentificationAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ShouldCreateUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() throws Exception {
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("should_create_user.json");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonStream, User.class);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        String result = userService.createUser(user.getUsername(), user.getPassword());

        assertEquals(String.format("Utilisateur %s crée avec succès", user.getUsername()), result);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
