package german.dev.onlychatbackend.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat_type")
    private Long id;

    @NotBlank(message = "The chat type is required")
    @Size(min = 4, max = 25, message = "The chat type must be between 4 and 20 characters")
    @Column(name = "name", nullable = false,unique = true, length = 20)
    private String name;

    public ChatType(Long id) {
        this.id = id;
    }

}
