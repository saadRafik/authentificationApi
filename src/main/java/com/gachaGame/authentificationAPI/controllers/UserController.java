package com.gachaGame.authentificationAPI.controllers;

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
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> userData) {
        String result = userService.createUser(userData.get("username"), userData.get("password"));
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> userData) {
        String result = userService.updatePassword(userData.get("username"), userData.get("oldPassword"), userData.get("newPassword"));
        return ResponseEntity.ok(Map.of("message", result));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> userData) {
        String result = userService.deleteUser(userData.get("username"), userData.get("password"));
        return ResponseEntity.ok(Map.of("message", result));
    }
}
