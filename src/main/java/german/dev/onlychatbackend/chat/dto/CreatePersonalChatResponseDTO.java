package german.dev.onlychatbackend.chat.dto;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreatePersonalChatResponseDTO {

    
    private Long id;
    private String name;
    private LocalDateTime joinedAt;
    private String chatType;
    private String otherUsername;

}
