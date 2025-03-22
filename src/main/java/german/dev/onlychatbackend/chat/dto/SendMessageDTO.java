package german.dev.onlychatbackend.chat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SendMessageDTO {

    @NotNull(message = "Chat id is required")
    Long chatId;

    @NotNull(message = "Message is required")
    @Size(min = 1, max = 255, message = "The message must be between 1 and 255 characters")
    String message;

    
}
