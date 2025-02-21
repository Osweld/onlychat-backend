package german.dev.onlychatbackend.user.controller;

import german.dev.onlychatbackend.user.entity.UserStatus;
import german.dev.onlychatbackend.user.service.UserStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-status")
public class UserStatusController {

    private final UserStatusService userStatusService;

    public UserStatusController(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @GetMapping
    ResponseEntity<List<UserStatus>> findAll(){
        return ResponseEntity.ok(userStatusService.findAll());
    }
}
