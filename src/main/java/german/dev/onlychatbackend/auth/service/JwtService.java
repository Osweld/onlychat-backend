package german.dev.onlychatbackend.auth.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.core.JsonProcessingException;
import german.dev.onlychatbackend.auth.models.AuthUser;
import io.jsonwebtoken.Claims;


public interface JwtService {

        String create(Authentication auth) throws NoSuchAlgorithmException, JsonProcessingException;

        boolean validate(String token);

        Claims getClaims(String token);

        String refreshToken(AuthUser authUser) throws JsonProcessingException;

        String getUsername(String token);

        Long getId(String token);

        Date getExpiration(String token);

        String resolve(String token);

        Collection<GrantedAuthority> getAuthorities(String token) throws IOException;

        Boolean requiresAuthentication(String header);

}
