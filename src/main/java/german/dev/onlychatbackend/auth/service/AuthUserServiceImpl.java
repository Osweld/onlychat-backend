package german.dev.onlychatbackend.auth.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import german.dev.onlychatbackend.auth.dto.LoginRequestDTO;
import german.dev.onlychatbackend.auth.dto.LoginResponseDTO;
import german.dev.onlychatbackend.auth.dto.SignUpRequestDTO;
import german.dev.onlychatbackend.auth.dto.SignUpResponseDTO;
import german.dev.onlychatbackend.auth.dto.TokenResponseDTO;
import german.dev.onlychatbackend.auth.mapper.AuthUserMapper;
import german.dev.onlychatbackend.auth.models.AuthUser;
import german.dev.onlychatbackend.rol.entity.Rol;
import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.entity.UserStatus;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;
import german.dev.onlychatbackend.user.repository.UserRepository;
import io.jsonwebtoken.io.IOException;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthUserMapper authUserMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthUserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
            AuthUserMapper authUserMapper, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authUserMapper = authUserMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequestDTO.getUsername()))) {
            throw new UnsupportedOperationException("Username already exists");
        }

        User user = authUserMapper.signUpRequestDTOToUser(signUpRequestDTO);
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        user.setRol(new Rol(Long.valueOf(RolEnum.USER.getId())));
        user.setUserStatus(new UserStatus(Long.valueOf(UserStatusEnum.INACTIVE.getId())));

        return authUserMapper.toAuthUserResponseDTO(userRepository.save(user));
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws IOException, JsonProcessingException, NoSuchAlgorithmException {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            String token = JwtServiceImpl.TOKEN_PREFIX + jwtService.create(authentication);
            

            return authUserMapper.toLoginResponseDTO(authUser,token, jwtService.getExpiration(token));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (DisabledException e) {
            throw new DisabledException("Account is not activated");
        }
    }

    @Override
    public TokenResponseDTO refreshToken(String token, AuthUser authUser) throws JsonProcessingException {
        if (jwtService.validate(token)) {
            String newToken = JwtServiceImpl.TOKEN_PREFIX + jwtService.refreshToken(authUser);
            return new TokenResponseDTO(newToken,jwtService.getExpiration(newToken));
        }  
        throw new UnsupportedOperationException("Invalid token");
        
    }

}
