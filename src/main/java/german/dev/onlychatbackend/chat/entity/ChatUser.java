package german.dev.onlychatbackend.chat.entity;

import java.time.LocalDateTime;

import german.dev.onlychatbackend.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ChatUserId.class)
public class ChatUser {

    private LocalDateTime joinedAt;

    @NotNull(message = "The chat is required")
    @Id
    @ManyToOne
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @NotNull(message = "The user is required")
    @Id
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @PrePersist
    protected void prePersist() {
        joinedAt = LocalDateTime.now();
    }

}
