package german.dev.onlychatbackend.auth.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import german.dev.onlychatbackend.auth.dto.LoginResponseDTO;
import german.dev.onlychatbackend.auth.dto.SignUpRequestDTO;
import german.dev.onlychatbackend.auth.dto.SignUpResponseDTO;
import german.dev.onlychatbackend.auth.models.AuthUser;
import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;

@Component
public class AuthUserMapper {

     public User signUpRequestDTOToUser(SignUpRequestDTO signUpRequestDTO) {
        User user = new User();
        user.setUsername(signUpRequestDTO.getUsername());
        user.setEmail(signUpRequestDTO.getEmail());
        return user;
    }

    public SignUpResponseDTO toAuthUserResponseDTO(User user) {
        SignUpResponseDTO dto = new SignUpResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRol(RolEnum.fromId(user.getRol().getId().intValue()));
        dto.setUserStatus(UserStatusEnum.fromId(user.getUserStatus().getId().intValue()));
        return dto;
    }


    public LoginResponseDTO toLoginResponseDTO(AuthUser user, String token, Date expirationDate) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setExpirationDate(expirationDate);
        dto.setToken(token);
        return dto;
    }


}
