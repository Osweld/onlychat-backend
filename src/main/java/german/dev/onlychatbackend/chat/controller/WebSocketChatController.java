package german.dev.onlychatbackend.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import german.dev.onlychatbackend.auth.model.AuthUser;
import german.dev.onlychatbackend.chat.dto.ChatMessageResponseDTO;
import german.dev.onlychatbackend.chat.dto.SendMessageDTO;
import german.dev.onlychatbackend.chat.entity.Message;
import german.dev.onlychatbackend.chat.service.MessageService;

@Controller
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload SendMessageDTO messageDTO, SimpMessageHeaderAccessor headerAccessor) {

        Authentication authentication = (Authentication) headerAccessor.getUser();

        if (authentication == null) {
            return;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AuthUser)) {
            throw new IllegalArgumentException("Invalid user principal");
        }

        AuthUser authUser = (AuthUser) principal;

        try {
            Long userId = authUser.getId();

            Message message = messageService.sendMessage(messageDTO, userId);

            ChatMessageResponseDTO responseDTO = ChatMessageResponseDTO.builder()
                    .id(message.getId())
                    .senderId(userId)
                    .chatId(messageDTO.getChatId())
                    .message(message.getText())
                    .timestamp(message.getSendAt())
                    .build();

            messagingTemplate.convertAndSend(
                    "/topic/chat." + messageDTO.getChatId(),
                    responseDTO);

        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                    authUser.getUsername(),
                    "/queue/errors",
                    "Send error : " + e.getMessage());
        }
    }
}
