package german.dev.onlychatbackend.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import german.dev.onlychatbackend.token.entity.TokenType;

public interface TokenTypeRepository extends JpaRepository<TokenType, Long> {

}
