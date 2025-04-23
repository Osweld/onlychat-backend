package german.dev.onlychatbackend.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import german.dev.onlychatbackend.chat.entity.Message;
import german.dev.onlychatbackend.chat.projection.ChatMessageProjection;

public interface MessageRepository extends JpaRepository<Message, Long> {

    

    @Query("SELECT m.id as id, m.text as message, m.sendAt as timestamp, m.chat.id as chatId, m.user.username as senderUsername, m.user.id as senderId FROM Message m WHERE m.chat.id = :chatId")
    List<ChatMessageProjection> getMessagesByChatId(Long chatId);

}
