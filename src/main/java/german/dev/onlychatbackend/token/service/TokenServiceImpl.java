package german.dev.onlychatbackend.token.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import german.dev.onlychatbackend.token.TokenTypeEnum;
import german.dev.onlychatbackend.token.entity.Token;
import german.dev.onlychatbackend.token.entity.TokenType;
import german.dev.onlychatbackend.token.exception.ValidateTokenException;
import german.dev.onlychatbackend.token.repository.TokenRepository;
import german.dev.onlychatbackend.user.entity.User;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    private static final Long ACTIVATE_ACCOUNT_TOKEN_TYPE_ID = Long.valueOf(TokenTypeEnum.ACTIVATE_ACCOUNT.getId());
    private static final Long RESET_PASSWORD_TOKEN_TYPE_ID = Long.valueOf(TokenTypeEnum.RESET_PASSWORD.getId());

    private static final String TOKEN_NOT_FOUND = "Token not found";

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public String generateActivateAccountToken(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Token token = generateToken(user, ACTIVATE_ACCOUNT_TOKEN_TYPE_ID);
        token.setExpiresAt(LocalDateTime.now().plusDays(15));
        tokenRepository.save(token);

        return token.getUuid();
    }

    @Override
    @Transactional
    public User activateAccountToken(String validateToken) {
        Token token = validateToken(
                tokenRepository.findByUuid(validateToken).orElseThrow(
                        () -> new ValidateTokenException(TOKEN_NOT_FOUND)),
                ACTIVATE_ACCOUNT_TOKEN_TYPE_ID);

        disabledToken(token);

        return token.getUser();

    }

    @Override
    @Transactional
    public String generateResetPasswordToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Token token = tokenRepository
                .findByUserAndTokenTypeAndIsUsedFalse(user, new TokenType(RESET_PASSWORD_TOKEN_TYPE_ID))
                .orElse(null);
        if (token != null) {
            disabledToken(token);
        }

        Token newToken = generateToken(user, RESET_PASSWORD_TOKEN_TYPE_ID);
        newToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(newToken);

        return newToken.getUuid();
    }

    @Override
    @Transactional
    public User resetPasswordToken(String validateToken) {
        Token token = validateToken(
                tokenRepository.findByUuid(validateToken).orElseThrow(
                        () -> new ValidateTokenException(TOKEN_NOT_FOUND)),
                RESET_PASSWORD_TOKEN_TYPE_ID);

        disabledToken(token);

        return token.getUser();

    }

    @Override
    @Transactional
    public String resendActivateAccountToken(User user) {
        Token token = tokenRepository
                .findByUserAndTokenTypeAndIsUsedFalse(user, new TokenType(ACTIVATE_ACCOUNT_TOKEN_TYPE_ID))
                .orElseThrow(() -> new ValidateTokenException(TOKEN_NOT_FOUND));

        disabledToken(token);
        token = generateToken(token.getUser(), ACTIVATE_ACCOUNT_TOKEN_TYPE_ID);
        token.setExpiresAt(LocalDateTime.now().plusDays(15));
        tokenRepository.save(token);

        return token.getUuid();
    }

    private Token validateToken(Token token, Long tokenTypeId) {
        if (token.getTokenType() == null ||
                !Objects.equals(token.getTokenType().getId(), tokenTypeId)) {
            throw new ValidateTokenException("Invalid token type");
        }

        if (Boolean.TRUE.equals(token.getIsUsed())) {
            throw new ValidateTokenException("Token already used");
        }

        if (token.getExpiresAt() == null) {
            throw new ValidateTokenException("Token invalid");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            disabledToken(token);

        }
        return token;
    }

    private Token generateToken(User user, Long tokenTypeId) {
        Token token = new Token();
        token.setUser(user);
        token.setTokenType(new TokenType(tokenTypeId));
        token.setUuid(UUID.randomUUID().toString());
        return token;
    }

    private void disabledToken(Token token) {
        token.setIsUsed(true);
        tokenRepository.save(token);

    }

}