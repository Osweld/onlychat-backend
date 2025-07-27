package german.dev.onlychatbackend.chat.projection;

import java.time.LocalDateTime;

public interface ChatInfoProjection {
    Long getId();
    String getName();
    LocalDateTime getJoinedAt();
    String getChatType();
    String getOtherUsername();
    String getLastMessage();
    LocalDateTime getLastMessageDate();
    Integer getUnreadCount();
}
