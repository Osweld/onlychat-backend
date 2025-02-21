package german.dev.onlychatbackend.user.repository;

import german.dev.onlychatbackend.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus,Long> {
}
