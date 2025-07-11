package german.dev.onlychatbackend.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import german.dev.onlychatbackend.chat.entity.ChatUser;
import german.dev.onlychatbackend.chat.entity.ChatUserId;
import german.dev.onlychatbackend.chat.projection.ChatInfoProjection;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {

    @Query("""
       SELECT ch.id as id, 
              ch.chatName as name, 
              cu.joinedAt as joinedAt, 
              ct.name as chatType,
              (SELECT u.username FROM ChatUser otherCu 
               JOIN otherCu.user u 
               WHERE otherCu.chat.id = ch.id AND otherCu.user.id != :userId 
               ORDER BY otherCu.id ASC FETCH FIRST 1 ROWS ONLY) as otherUsername,
              m.text as lastMessage, 
              m.sendAt as lastMessageDate,
              m.isRead as isRead
       FROM ChatUser cu
       JOIN cu.chat ch
       JOIN ch.chatType ct
       LEFT JOIN Message m ON m.chat.id = ch.id AND m.sendAt = (
           SELECT MAX(m2.sendAt) FROM Message m2 WHERE m2.chat.id = ch.id
       )
       WHERE cu.user.id = :userId
       ORDER BY m.sendAt DESC NULLS LAST
       """)
    List<ChatInfoProjection> findByUserId(Long userId);

    Boolean existsByChatIdAndUserId(Long chatId, Long userId);


    @Query("""
       SELECT cu.user.id
       FROM ChatUser cu
       WHERE cu.chat.id = :chatId AND cu.user.id != :userId
       """)
    List<Long> findOtherUserIdsInChat(Long chatId, Long userId);
}
