package german.dev.onlychatbackend.user.service;

import german.dev.onlychatbackend.user.entity.UserStatus;
import german.dev.onlychatbackend.user.repository.UserStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserStatusServiceImpl implements UserStatusService {


    private final UserStatusRepository userStatusRepository;

    public UserStatusServiceImpl(UserStatusRepository userStatusRepository) {
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserStatus> findAll() {
        return userStatusRepository.findAll();
    }
}
