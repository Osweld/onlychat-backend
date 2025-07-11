package german.dev.onlychatbackend.chat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import german.dev.onlychatbackend.chat.dto.SendMessageDTO;
import german.dev.onlychatbackend.chat.entity.Message;
import german.dev.onlychatbackend.chat.projection.ChatMessageProjection;

public interface MessageService {

    Page<ChatMessageProjection> getMessagesByChatId(Long chatId, Long userId, Pageable pageable);
    Message sendMessage(SendMessageDTO message, Long userId);
    void markChatAsRead(Long chatId, Long userId);
}
