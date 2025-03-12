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
public class ShouldUpdatePasswordTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldUpdatePassword() throws Exception {
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("should_update_password.json");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonStream, User.class);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        String result = userService.updatePassword(user.getUsername(), user.getPassword(), "newPassword123");

        assertEquals("Mot de passe mis à jour", result);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
