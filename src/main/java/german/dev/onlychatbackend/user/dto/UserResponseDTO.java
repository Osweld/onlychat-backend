package german.dev.onlychatbackend.user.dto;

import lombok.Data;
import java.time.LocalDateTime;

import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private RolEnum rol;
    private UserStatusEnum userStatus;
}