package german.dev.onlychatbackend.user.service;

import german.dev.onlychatbackend.user.entity.UserStatus;
import german.dev.onlychatbackend.user.exception.UserStatusNotFoundException;
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
        try{
            return userStatusRepository.findAll();
        }catch (Exception e){
            throw new UserStatusNotFoundException("Error while fetching user status");
        }
        
    }
}
