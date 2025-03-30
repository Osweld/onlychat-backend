package german.dev.onlychatbackend.chat.mapper;

import org.springframework.stereotype.Component;

import german.dev.onlychatbackend.chat.dto.CreatePersonalChatResponseDTO;

@Component
public class ChatMapper {


    public CreatePersonalChatResponseDTO toCreatePersonalChatResponseDTO(Long chatId, String chatName, String chatType, String username, String otherUsername) {
        CreatePersonalChatResponseDTO dto = new CreatePersonalChatResponseDTO();
        dto.setChatId(chatId);
        dto.setChatName(chatName);
        dto.setChatType(chatType);
        dto.setUsername(username);
        dto.setOtherUsername(otherUsername);
        return dto;
    }


}
