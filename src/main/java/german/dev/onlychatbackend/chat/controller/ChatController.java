package german.dev.onlychatbackend.chat.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import german.dev.onlychatbackend.auth.model.AuthUser;
import german.dev.onlychatbackend.chat.dto.CreatePersonalChatRequestDTO;
import german.dev.onlychatbackend.chat.dto.CreatePersonalChatResponseDTO;
import german.dev.onlychatbackend.chat.dto.UserChatsResponseDTO;
import german.dev.onlychatbackend.chat.projection.UserSearchProjection;
import german.dev.onlychatbackend.chat.service.ChatService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/user-chats")
    public ResponseEntity<UserChatsResponseDTO> getUserChats(@AuthenticationPrincipal Principal principal) {
        AuthUser authUser = (AuthUser) principal;
        return ResponseEntity.ok(chatService.getUserChats(authUser.getId()));
    }

    @PostMapping()
    public ResponseEntity<CreatePersonalChatResponseDTO> createPersonalChat(@AuthenticationPrincipal Principal principal, @RequestBody @Valid CreatePersonalChatRequestDTO createPersonalChatRequestDTO) {
        AuthUser authUser = (AuthUser) principal;
        return ResponseEntity.ok(chatService.createPersonalChat(authUser.getUsername(), createPersonalChatRequestDTO));
    }

    @GetMapping("/search-users")
    public ResponseEntity<Page<UserSearchProjection>> searchUsers(@RequestParam String username, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(chatService.searchUsers(username, PageRequest.of(page, size)));
    
    }
}
