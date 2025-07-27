package german.dev.onlychatbackend.chat.projection;

import java.time.LocalDateTime;

public interface ChatMessageProjection {

    Long getId();
    Long getChatId();
    String getMessage();
    LocalDateTime getTimestamp();
    Long getSenderId();
    Boolean getIsRead();
    String getSenderUsername();

}
