package com.gachaGame.authentificationAPI.controllers;

import com.gachaGame.authentificationAPI.domain.UpdatePasswordRequestDto;
import com.gachaGame.authentificationAPI.domain.UserRequestDto;
import com.gachaGame.authentificationAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userData) {
        String result = userService.createUser(userData.getUsername(), userData.getPassword());
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDto userData) {
        String result = userService.updatePassword(userData.getUsername(), userData.getOldPassword(), userData.getNewPassword());
        return ResponseEntity.ok(Map.of("message", result));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> userData) {
        String result = userService.deleteUser(userData.get("username"), userData.get("password"));
        return ResponseEntity.ok(Map.of("message", result));
    }
}
