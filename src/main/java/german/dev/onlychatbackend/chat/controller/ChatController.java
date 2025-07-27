package german.dev.onlychatbackend.chat.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import german.dev.onlychatbackend.auth.model.AuthUser;
import german.dev.onlychatbackend.chat.dto.CreatePersonalChatRequestDTO;
import german.dev.onlychatbackend.chat.dto.CreatePersonalChatResponseDTO;
import german.dev.onlychatbackend.chat.dto.UserChatsResponseDTO;
import german.dev.onlychatbackend.chat.projection.ChatMessageProjection;
import german.dev.onlychatbackend.chat.projection.UserSearchProjection;
import german.dev.onlychatbackend.chat.service.ChatService;
import german.dev.onlychatbackend.chat.service.MessageService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }


    @GetMapping("/user-chats")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<UserChatsResponseDTO> getUserChats(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.ok(chatService.getUserChats(authUser.getId()));
    }

    @PostMapping()
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<CreatePersonalChatResponseDTO> createPersonalChat(@AuthenticationPrincipal AuthUser authUser, @RequestBody @Valid CreatePersonalChatRequestDTO createPersonalChatRequestDTO) {
        return ResponseEntity.ok(chatService.createPersonalChat(authUser.getUsername(), createPersonalChatRequestDTO));
    }

    @GetMapping("/search-users")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Page<UserSearchProjection>> searchUsers(@RequestParam String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(chatService.searchUsers(username, PageRequest.of(page, size)));
    
    }

    @GetMapping("/messages/{chatId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Page<ChatMessageProjection>> getMessagesByChatId(@PathVariable Long chatId, @AuthenticationPrincipal AuthUser authUser, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size) {
        return ResponseEntity.ok(messageService.getMessagesByChatId(chatId, authUser.getId(), PageRequest.of(page, size)));
    }

    @PostMapping("/messages/mark-as-read/{chatId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Void> markChatAsRead(@PathVariable Long chatId, @AuthenticationPrincipal AuthUser authUser) {
        messageService.markChatAsRead(chatId, authUser.getId());
        return ResponseEntity.noContent().build();
    }
}
