package com.gachaGame.authentificationAPI.services;

import com.gachaGame.authentificationAPI.dataAccess.*;
import com.gachaGame.authentificationAPI.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public String createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Nom d'utilisateur déjà pris";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return "Utilisateur créé avec succès";
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
        return "Utilisateur non trouvé";
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
            return "Utilisateur supprimé";
        }
        return "Utilisateur non trouvé";
    }
}
