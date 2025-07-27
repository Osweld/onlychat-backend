package german.dev.onlychatbackend.user.service;

import german.dev.onlychatbackend.user.entity.UserStatus;

import java.util.List;

public interface UserStatusService {

    List<UserStatus> findAll();
}
