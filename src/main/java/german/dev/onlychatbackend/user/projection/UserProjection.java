package german.dev.onlychatbackend.user.projection;

import java.time.LocalDateTime;

import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;

public interface UserProjection {

    Long getId();
    String getUsername();
    String getEmail();
    LocalDateTime getCreatedAt();
    RolEnum getRol();
    UserStatusEnum getUserStatus();
}
