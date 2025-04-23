package german.dev.onlychatbackend.chat.service;

import java.util.List;

import german.dev.onlychatbackend.chat.dto.SendMessageDTO;
import german.dev.onlychatbackend.chat.entity.Message;
import german.dev.onlychatbackend.chat.projection.ChatMessageProjection;

public interface MessageService {

    List<ChatMessageProjection> getMessagesByChatId(Long chatId, Long userId);
    Message sendMessage(SendMessageDTO message, Long userId);




}
