package german.dev.onlychatbackend.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import german.dev.onlychatbackend.auth.dto.LoginRequestDTO;
import german.dev.onlychatbackend.auth.dto.LoginResponseDTO;
import german.dev.onlychatbackend.auth.dto.SignUpRequestDTO;
import german.dev.onlychatbackend.auth.dto.SignUpResponseDTO;
import german.dev.onlychatbackend.auth.dto.TokenResponseDTO;
import german.dev.onlychatbackend.auth.model.AuthUser;
import german.dev.onlychatbackend.auth.service.AuthUserService;
import io.jsonwebtoken.io.IOException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthUserService authUserService;

    public AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PermitAll()
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signup(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        return new ResponseEntity<>(authUserService.signUp(signUpRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PermitAll()
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO)
            throws IOException, JsonProcessingException, NoSuchAlgorithmException {
        return new ResponseEntity<>(authUserService.login(loginRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<TokenResponseDTO> refreshToken(
            @RequestHeader("Authorization") String token,
            @AuthenticationPrincipal AuthUser authUser) throws JsonProcessingException {
        return new ResponseEntity<>(
                authUserService.refreshToken(token, authUser),
                HttpStatus.CREATED);
    }

}
