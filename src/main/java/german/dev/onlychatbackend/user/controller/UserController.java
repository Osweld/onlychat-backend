package german.dev.onlychatbackend.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import german.dev.onlychatbackend.user.projection.UserProjection;
import german.dev.onlychatbackend.user.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping("")
    ResponseEntity<Page<UserProjection>> getAllUsers (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(userService.findAllUsers(PageRequest.of(page, size)));
    }
    

}
