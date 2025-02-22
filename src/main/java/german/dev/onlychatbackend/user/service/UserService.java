package german.dev.onlychatbackend.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import german.dev.onlychatbackend.user.dto.UserRequestDTO;
import german.dev.onlychatbackend.user.dto.UserResponseDTO;
import german.dev.onlychatbackend.user.dto.UserUpdateDTO;
import german.dev.onlychatbackend.user.projection.UserProjection;

public interface UserService {

    UserProjection findUserById(Long id);
    UserProjection findUserbyUsername(String username);
    Page<UserProjection> searchUserByUsername(String username, Pageable pageable);
    Page<UserProjection> findAllUsers(Pageable pageable);
    UserResponseDTO saveUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);
    void deleteUser(Long id);

    

}
