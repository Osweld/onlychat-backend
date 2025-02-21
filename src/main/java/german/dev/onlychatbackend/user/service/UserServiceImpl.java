package german.dev.onlychatbackend.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import german.dev.onlychatbackend.user.projection.UserProjection;
import german.dev.onlychatbackend.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProjection findUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public UserProjection findUserbyUsername(String username) {
        return userRepository.findUserbyUsername(username).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProjection> searchUserByUsername(String username, Pageable pageable) {
        return userRepository.searchUserByUsername(username, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProjection> findAllUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable);
    }

}
