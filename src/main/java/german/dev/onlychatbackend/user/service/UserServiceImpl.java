package german.dev.onlychatbackend.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import german.dev.onlychatbackend.rol.entity.Rol;
import german.dev.onlychatbackend.rol.enums.RolEnum;
import german.dev.onlychatbackend.user.dto.UserRequestDTO;
import german.dev.onlychatbackend.user.dto.UserResponseDTO;
import german.dev.onlychatbackend.user.dto.UserUpdateDTO;
import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.entity.UserStatus;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;
import german.dev.onlychatbackend.user.mapper.UserMapper;
import german.dev.onlychatbackend.user.projection.UserProjection;
import german.dev.onlychatbackend.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    @Override
    @Transactional
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user.setRol(new Rol(Long.valueOf(RolEnum.USER.getId())));
        user.setUserStatus(new UserStatus(Long.valueOf(UserStatusEnum.INACTIVE.getId())));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User userToUpdate = userRepository.findById(id).orElseThrow();
        userMapper.updateEntity(userToUpdate, userUpdateDTO);
        return userMapper.toDto(userRepository.save(userToUpdate));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow();
        userToDelete.setUserStatus(new UserStatus(Long.valueOf(UserStatusEnum.DELETED.getId())));
        userRepository.save(userToDelete);
    }

}
