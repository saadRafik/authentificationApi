package authCom.dataAccess;

import authCom.domain.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    void deleteByUsername(String username);
}


