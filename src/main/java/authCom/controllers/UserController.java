package authCom.controllers;

import authCom.dto.UpdatePasswordRequestDto;
import authCom.dto.UserRequestDto;
import authCom.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRequestDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userData) {
        String result = userService.createUser(userData.getUsername(), userData.getPassword());
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDto userData) {
        String result = userService.updatePassword(userData.getUsername(), userData.getOldPassword(), userData.getNewPassword());

        if (result.equals("Ancien mot de passe incorrect")) {
            return ResponseEntity.status(400).body(Map.of("error", result));
        }

        return ResponseEntity.ok(Map.of("message", result));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserRequestDto userData) {
        String result = userService.deleteUser(userData.getUsername(), userData.getPassword());

        if (result.contains("non trouvé")) {
            return ResponseEntity.status(404).body(Map.of("error", result));
        }

        return ResponseEntity.ok(Map.of("message", result));
    }
}
