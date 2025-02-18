package com.gachaGame.authentificationAPI.dataAccess;

import com.gachaGame.authentificationAPI.domain.Token;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TokenRepository {
    Optional<Token> findByToken(String token);
    void deleteByUsername(String username);
}


