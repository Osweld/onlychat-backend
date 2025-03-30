package german.dev.onlychatbackend.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import german.dev.onlychatbackend.chat.entity.Message;
import german.dev.onlychatbackend.chat.projection.ChatMessageProjection;

public interface MessageRepository extends JpaRepository<Message, Long> {

    

    @Query("SELECT m.id as id, m.text as text, m.sendAt as sendAt, m.chat.chatName as chat, m.user.username as user FROM Message m WHERE m.chat.id = :chatId")
    List<ChatMessageProjection> getMessagesByChatId(Long chatId);

}
