package german.dev.onlychatbackend.token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import german.dev.onlychatbackend.token.entity.Token;
import german.dev.onlychatbackend.token.entity.TokenType;
import german.dev.onlychatbackend.user.entity.User;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUuid(String uuid);


    Optional<Token> findByUserAndTokenTypeAndIsUsedFalse(User user, TokenType tokenType);


}
