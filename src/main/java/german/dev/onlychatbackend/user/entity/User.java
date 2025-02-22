package german.dev.onlychatbackend.user.entity;

import german.dev.onlychatbackend.rol.entity.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotBlank(message = "The username is required")
    @Size(min=5, max = 20, message = "The username must be between 5 and 20 characters")
    @Column(name = "username", nullable = false, length = 20, unique = true)
    private String username;

    @NotBlank(message = "The password is required")
    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @NotBlank(message = "The email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email format")
    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @NotNull(message = "Created at is required")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "The rol is required")
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @NotNull(message = "The user status is required")
    @ManyToOne
    @JoinColumn(name = "id_user_status", nullable = false)
    private UserStatus userStatus;


    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
