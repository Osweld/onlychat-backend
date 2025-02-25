package german.dev.onlychatbackend.auth.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import german.dev.onlychatbackend.auth.mixin.GrantedAuthorityMixin;
import german.dev.onlychatbackend.auth.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    private final String secretKey;
    public static final long EXPIRATION_TIME = 100 * 60 * 60 * 8L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public JwtServiceImpl(@Value("${secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String create(Authentication auth) throws NoSuchAlgorithmException, JsonProcessingException {
        AuthUser authUser = (AuthUser) auth.getPrincipal();
        return generateToken(authUser);
    }

    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
                .parseSignedClaims(resolve(token)).getPayload();
    }

    @Override
    public String refreshToken(AuthUser authUser) throws JsonProcessingException {
        return generateToken(authUser);
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Long getId(String token) {
        return Long.parseLong(getClaims(token).getId());
    }

    @Override
    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    @Override
    public String resolve(String token) {
        if (token == null || !token.startsWith(TOKEN_PREFIX))
            return null;
        return token.replace(TOKEN_PREFIX, "");

    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(String token) throws IOException {
        String authorities = (String) getClaims(token).get("authorities");
        return Arrays.asList(new ObjectMapper()
        .addMixIn(SimpleGrantedAuthority.class, GrantedAuthorityMixin.class)
        .readValue(authorities.getBytes(), SimpleGrantedAuthority[].class));
    }

    @Override
    public Boolean requiresAuthentication(String header) {
    return (header == null || !header.startsWith(TOKEN_PREFIX));
    }

    private String generateToken(AuthUser authUser) throws JsonProcessingException {
        Collection<GrantedAuthority> authorities = authUser.getAuthorities();

        Map<String, String> claims = new HashMap<>();
        claims.put("authorities", new ObjectMapper().writeValueAsString(authorities));

        return Jwts.builder()
                .claims(claims)
                .id(authUser.getId().toString())
                .subject(authUser.getUsername())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

}
