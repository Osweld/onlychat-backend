package german.dev.onlychatbackend.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import german.dev.onlychatbackend.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Boolean existsByChatNameAndChatTypeId(String chatName, Long chatTypeId);

    
}
