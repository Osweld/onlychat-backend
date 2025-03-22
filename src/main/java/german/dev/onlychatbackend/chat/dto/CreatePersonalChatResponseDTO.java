package german.dev.onlychatbackend.chat.dto;

import lombok.Data;

@Data
public class CreatePersonalChatResponseDTO {

    
    private Long chatId;
    private String chatName;
    private String chatType;
    private String username;
    private String otherUsername;

}
