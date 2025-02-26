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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import german.dev.onlychatbackend.auth.dto.LoginRequestDTO;
import german.dev.onlychatbackend.auth.dto.LoginResponseDTO;
import german.dev.onlychatbackend.auth.dto.SignUpRequestDTO;
import german.dev.onlychatbackend.auth.dto.SignUpResponseDTO;
import german.dev.onlychatbackend.auth.dto.TokenResponseDTO;
import german.dev.onlychatbackend.auth.mapper.AuthUserMapper;
import german.dev.onlychatbackend.auth.model.AuthUser;
import german.dev.onlychatbackend.rol.entity.Rol;
import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.entity.UserStatus;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;
import german.dev.onlychatbackend.user.exception.UserAlreadyExistsException;
import german.dev.onlychatbackend.user.repository.UserRepository;
import io.jsonwebtoken.io.IOException;


@Service
public class AuthUserServiceImpl implements AuthUserService {

    private static final Long USER_ROLE_ID = Long.valueOf(RolEnum.USER.getId());
    private static final Long INACTIVE_STATUS_ID = Long.valueOf(UserStatusEnum.INACTIVE.getId());

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
    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequestDTO.getUsername()))) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequestDTO.getEmail()))) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = authUserMapper.signUpRequestDTOToUser(signUpRequestDTO);
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        user.setRol(new Rol(USER_ROLE_ID));
        user.setUserStatus(new UserStatus(INACTIVE_STATUS_ID));

        return authUserMapper.toAuthUserResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional
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
    @Transactional
    public TokenResponseDTO refreshToken(String token, AuthUser authUser) throws JsonProcessingException {
        if (jwtService.validate(token)) {
            String newToken = JwtServiceImpl.TOKEN_PREFIX + jwtService.refreshToken(authUser);
            return new TokenResponseDTO(newToken,jwtService.getExpiration(newToken));
        }  
        throw new UnsupportedOperationException("Invalid token");
        
    }

}
