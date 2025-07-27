package german.dev.onlychatbackend.token.entity;

import java.time.LocalDateTime;
import german.dev.onlychatbackend.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long id;

    @NotBlank(message = "The token is required")
    @Size(max = 255, message = "The token must be at most 255 characters")
    @Column(name = "uuid", nullable = false, length = 255)
    private String uuid;

    @NotNull(message = "Created at is required")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Expired at is required")
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @NotNull(message = "Is used status is required")
    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @NotNull(message = "The user is required")
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @NotNull(message = "The token type is required")
    @ManyToOne
    @JoinColumn(name = "id_token_type", nullable = false)
    private TokenType tokenType;

    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now();
        isUsed = false;
    }
}
