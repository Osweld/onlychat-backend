package german.dev.onlychatbackend.auth.dto;

import java.time.LocalDateTime;

import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;
import lombok.Data;

@Data
public class SignUpResponseDTO {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private RolEnum rol;
    private UserStatusEnum userStatus;
}
