package german.dev.onlychatbackend.chat.dto;

import lombok.Data;

@Data
public class MessageSeenNotificationDTO {

    private Long chatId;
    private Long userId;

    public MessageSeenNotificationDTO(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }
}
