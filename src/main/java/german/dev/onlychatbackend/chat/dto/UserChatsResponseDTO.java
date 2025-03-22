package german.dev.onlychatbackend.chat.dto;

import java.util.List;

import german.dev.onlychatbackend.chat.projection.ChatInfoProjection;
import lombok.Data;

@Data
public class UserChatsResponseDTO {

    private Long id;
    private String username;
    private List<ChatInfoProjection> chats;

}
