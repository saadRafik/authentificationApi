package com.gachaGame.authentificationAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachaGame.authentificationAPI.dataAccess.UserRepository;
import com.gachaGame.authentificationAPI.domain.User;
import com.gachaGame.authentificationAPI.domain.UserRequestDto;
import com.gachaGame.authentificationAPI.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShouldGetAllUsersTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldGetAllUsers() throws Exception {
        InputStream jsonStream = getClass().getClassLoader().getResourceAsStream("should_get_all_users.json");
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(jsonStream, new TypeReference<List<User>>(){});

        when(userRepository.findAll()).thenReturn(users);

        List<UserRequestDto> result = userService.getAll();

        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getUsername(), result.get(0).getUsername());
    }
}

