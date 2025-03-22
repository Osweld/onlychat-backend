package german.dev.onlychatbackend.chat.projection;

import java.time.LocalDateTime;

public interface ChatMessageProjection {

    Long getId();
    String getText();
    LocalDateTime getSendAt();
    String getChat();
    String getUser();

}
