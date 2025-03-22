package german.dev.onlychatbackend.chat.entity;

import java.time.LocalDateTime;

import german.dev.onlychatbackend.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Long id;

    @NotBlank(message = "Message is required")
    @Size(min = 1, max = 255, message = "The message must be between 1 and 255 characters")
    @Column(name = "message", nullable = false, length = 255)
    private String text;

    @NotNull(message = "Send at is required")
    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @NotNull(message = "Is deleted status is required")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @NotNull(message = "The chat is required")
    @ManyToOne
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @NotNull(message = "The user is required")
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;


    @PrePersist
    protected void prePersist() {
        sendAt = LocalDateTime.now();
        isDeleted = false;
    }

}
