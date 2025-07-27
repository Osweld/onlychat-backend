package german.dev.onlychatbackend.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import german.dev.onlychatbackend.auth.dto.ActivateAccountResponseDTO;
import german.dev.onlychatbackend.auth.dto.ChangePasswordRequestDTO;
import german.dev.onlychatbackend.auth.dto.ChangePasswordResponseDTO;
import german.dev.onlychatbackend.auth.dto.LoginRequestDTO;
import german.dev.onlychatbackend.auth.dto.LoginResponseDTO;
import german.dev.onlychatbackend.auth.dto.PasswordResetRequestDTO;
import german.dev.onlychatbackend.auth.dto.PasswordResetResponseDTO;
import german.dev.onlychatbackend.auth.dto.ResendActivateAccountTokenRequestDTO;
import german.dev.onlychatbackend.auth.dto.ResendActivateAccountTokenResponseDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(authUserService.signUp(signUpRequestDTO));
    }

    @PostMapping("/login")
    @PermitAll()
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO)
            throws IOException, JsonProcessingException, NoSuchAlgorithmException {
        return ResponseEntity.ok(authUserService.login(loginRequestDTO));
    }

    @PostMapping("/refresh-token")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<TokenResponseDTO> refreshToken(
            @RequestHeader("Authorization") String token,
            @AuthenticationPrincipal AuthUser authUser) throws JsonProcessingException {
        return ResponseEntity.ok(
                authUserService.refreshToken(token, authUser)
        );
    }

    @GetMapping("/activate-account/{token}")
    @PermitAll()
    public ResponseEntity<ActivateAccountResponseDTO> activateAccount(@PathVariable String token) {
        return ResponseEntity.ok(authUserService.activateAccount(token));
    }

    @PostMapping("/resend-activate-account-token")
    @PermitAll()
    public ResponseEntity<ResendActivateAccountTokenResponseDTO> resendActivateAccountToken(
            @RequestBody @Valid ResendActivateAccountTokenRequestDTO resendActivateAccountTokenRequestDTO) {
        return ResponseEntity.ok(authUserService.resendActivateAccountToken(resendActivateAccountTokenRequestDTO));
    }

    @GetMapping("/reset-password")
    @PermitAll()
    public ResponseEntity<PasswordResetResponseDTO> requestPasswordReset(@RequestParam String email) {
        return ResponseEntity.ok(authUserService.requestPasswordReset(email));
    }


    @PostMapping("/reset-password")
    @PermitAll()
    public ResponseEntity<PasswordResetResponseDTO> resetPassword(
            @RequestParam(required = true) String token,
            @RequestBody @Valid PasswordResetRequestDTO passwordResetRequest) {
        return ResponseEntity.ok(authUserService.resetPassword(token, passwordResetRequest));
    }

    @PostMapping("/change-password")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(
            @RequestBody @Valid ChangePasswordRequestDTO changePasswordRequestDTO,
            @AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.ok(authUserService.changePassword(changePasswordRequestDTO, authUser));
    }

}
