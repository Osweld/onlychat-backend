package german.dev.onlychatbackend.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import german.dev.onlychatbackend.user.dto.UserRequestDTO;
import german.dev.onlychatbackend.user.dto.UserResponseDTO;
import german.dev.onlychatbackend.user.dto.UserUpdateDTO;
import german.dev.onlychatbackend.user.projection.UserProjection;
import german.dev.onlychatbackend.user.service.UserService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    ResponseEntity<UserProjection> getUserById (@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/username/{username}")
    ResponseEntity<UserProjection> getUserByUsername (@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserbyUsername(username));
    }

    @GetMapping("/search/{username}")
    ResponseEntity<Page<UserProjection>> searchUserByUsername (@PathVariable String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(userService.searchUserByUsername(username,PageRequest.of(page, size)));
    }

    @GetMapping()
    ResponseEntity<Page<UserProjection>> getAllUsers (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(userService.findAllUsers(PageRequest.of(page, size)));
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
       return new ResponseEntity<>(userService.saveUser(userRequestDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        return new ResponseEntity<>(userService.updateUser(id, userUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    

}
