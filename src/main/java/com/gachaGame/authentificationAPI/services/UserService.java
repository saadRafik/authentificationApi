package com.gachaGame.authentificationAPI.services;

import com.gachaGame.authentificationAPI.dataAccess.*;
import com.gachaGame.authentificationAPI.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public List<UserRequestDto> getAll() {

        return userRepository.findAll().stream()
                .map(user -> new UserRequestDto(user.getUsername(), user.getPassword()))
                .collect(Collectors.toList());
    }



    public String createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {

            return String.format("Nom d'utilisateur %s déjà pris", username != null ? username : "inconnu");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);

        return  String.format("Utilisateur %s avec succès", username != null ? username : "inconnu");
    }

    public String updatePassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (!user.getPassword().equals(oldPassword)) {
                return "Ancien mot de passe incorrect";
            }

            user.setPassword(newPassword);
            userRepository.save(user);
            return "Mot de passe mis à jour";
        }

        return String.format("Utilisateur %s non trouvé", username != null ? username : "inconnu");
    }


    @Transactional
    public String deleteUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (!user.getPassword().equals(password)) {
                return "Mot de passe incorrect";
            }

            tokenRepository.deleteByUsername(username);
            userRepository.delete(user);

            return String.format("Utilisateur %s supprimé", username != null ? username : "inconnu");
        }

        return String.format("Utilisateur %s non trouvé", username != null ? username : "inconnu");
    }
}
