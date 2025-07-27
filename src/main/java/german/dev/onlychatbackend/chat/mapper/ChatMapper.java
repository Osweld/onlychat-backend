package german.dev.onlychatbackend.chat.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import german.dev.onlychatbackend.chat.dto.CreatePersonalChatResponseDTO;

@Component
public class ChatMapper {


    public CreatePersonalChatResponseDTO toCreatePersonalChatResponseDTO(Long chatId, String chatName, String chatType, String otherUsername, LocalDateTime joinedAt) {
        CreatePersonalChatResponseDTO dto = new CreatePersonalChatResponseDTO();
        dto.setId(chatId);
        dto.setName(chatName);
        dto.setChatType(chatType);
        dto.setOtherUsername(otherUsername);
        dto.setJoinedAt(joinedAt);
        return dto;
    }


}
