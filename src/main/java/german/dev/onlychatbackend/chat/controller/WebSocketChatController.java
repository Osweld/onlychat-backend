package german.dev.onlychatbackend.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import german.dev.onlychatbackend.auth.model.AuthUser;
import german.dev.onlychatbackend.chat.dto.SendMessageDTO;
import german.dev.onlychatbackend.chat.service.MessageService;

import java.security.Principal;

@Controller
public class WebSocketChatController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }
    
    
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload SendMessageDTO messageDTO, Principal principal) {
        try {
            AuthUser authUser = (AuthUser) principal;
            Long userId = authUser.getId();
            
            messageService.sendMessage(messageDTO, userId);
            
            messagingTemplate.convertAndSend(
                    "/topic/chat." + messageDTO.getChatId(), 
                    messageDTO);
            
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/errors",
                "Error al enviar mensaje: " + e.getMessage()
            );
        }
    }
}
