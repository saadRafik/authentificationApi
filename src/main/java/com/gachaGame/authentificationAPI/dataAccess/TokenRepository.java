package com.gachaGame.authentificationAPI.dataAccess;

import com.gachaGame.authentificationAPI.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    void deleteByUsername(String username);
}


