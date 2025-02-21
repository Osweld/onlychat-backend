package german.dev.onlychatbackend.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_status")
    private Long id;

    @NotBlank(message = "The Status name is required")
    @Size(min=5, max = 25, message = "The Status name must be between 5 and 25 characters")
    @Column(name = "name", nullable = false, length = 25, unique = true)
    private String name;
}
