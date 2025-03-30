package german.dev.onlychatbackend.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import german.dev.onlychatbackend.chat.entity.ChatUser;
import german.dev.onlychatbackend.chat.entity.ChatUserId;
import german.dev.onlychatbackend.chat.projection.ChatInfoProjection;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {

    @Query("""
           SELECT ch.id as id, ch.chatName as name, cu.joinedAt, ct.name as chatType, 
                  (SELECT u.username FROM ChatUser otherCu 
                   JOIN otherCu.user u 
                   WHERE otherCu.chat.id = ch.id AND otherCu.user.id != :userId 
                   ORDER BY otherCu.joinedAt ASC) as otherUsername 
           FROM ChatUser cu 
           JOIN cu.chat ch 
           JOIN ch.chatType ct 
           WHERE cu.user.id = :userId
           """)
    List<ChatInfoProjection> findByUserId(Long userId);

    Boolean existsByChatIdAndUserId(Long chatId, Long userId);

}
