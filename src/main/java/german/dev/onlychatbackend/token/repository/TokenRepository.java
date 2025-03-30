package german.dev.onlychatbackend.token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import german.dev.onlychatbackend.token.entity.Token;
import german.dev.onlychatbackend.token.entity.TokenType;
import german.dev.onlychatbackend.user.entity.User;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUuid(String uuid);

    Optional<Token> findByUserAndTokenTypeAndIsUsedFalse(User user, TokenType tokenType);

    @Modifying
    @Query("UPDATE Token t SET t.isUsed = true WHERE t.user.id = :userId AND t.tokenType.id = :tokenTypeId")
    void disableAllTokensForUser(
            @Param("userId") Long userId,
            @Param("tokenTypeId") Long tokenTypeId);

}
