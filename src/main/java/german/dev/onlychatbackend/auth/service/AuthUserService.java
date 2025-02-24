package german.dev.onlychatbackend.auth.service;

import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;

import german.dev.onlychatbackend.auth.dto.LoginRequestDTO;
import german.dev.onlychatbackend.auth.dto.LoginResponseDTO;
import german.dev.onlychatbackend.auth.dto.SignUpRequestDTO;
import german.dev.onlychatbackend.auth.dto.SignUpResponseDTO;
import german.dev.onlychatbackend.auth.dto.TokenResponseDTO;
import german.dev.onlychatbackend.auth.models.AuthUser;
import io.jsonwebtoken.io.IOException;


public interface AuthUserService {

    SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws IOException, JsonProcessingException, NoSuchAlgorithmException;
    TokenResponseDTO refreshToken(String token, AuthUser authUser) throws JsonProcessingException;


}
