package german.dev.onlychatbackend.user.mapper;

import org.springframework.stereotype.Component;
import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;
import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.dto.UserRequestDTO;
import german.dev.onlychatbackend.user.dto.UserResponseDTO;
import german.dev.onlychatbackend.user.dto.UserUpdateDTO;

@Component
public class UserMapper {
    
    public User toEntity(UserRequestDTO dto) {

        if(dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public void updateEntity(User user, UserUpdateDTO dto) {
        if(user == null || dto == null) {
            throw new IllegalArgumentException("User or UserUpdate cannot be null");
        }

        if(dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
    }

    public UserResponseDTO toDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRol(RolEnum.fromId(user.getRol().getId().intValue()));
        dto.setUserStatus(UserStatusEnum.fromId(user.getUserStatus().getId().intValue()));
        return dto;
    }
}