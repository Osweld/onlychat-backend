package german.dev.onlychatbackend.chat.entity;

import java.time.LocalDateTime;

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
@Table(name = "chats")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long id;

    @NotBlank(message = "The chat name is required")
    @Size(min=5, max = 50, message = "The chat must be between 5 and 50 characters")
    @Column(name = "chat_name", nullable = false, length = 50, unique = true)
    private String chatName;

    @NotNull(message = "Created at is required")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Chat type is required")
    @ManyToOne
    @JoinColumn(name = "id_chat_type", nullable = false)
    private ChatType chatType;

    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Chat(Long id) {
        this.id = id;
    }

}
