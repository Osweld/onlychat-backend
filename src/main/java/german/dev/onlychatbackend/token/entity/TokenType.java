package german.dev.onlychatbackend.token.entity;

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
@Table(name = "token_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token_type")
    private Long id;

    @NotBlank(message = "The token type is required")
    @Size(min = 4, max = 25, message = "The token type must be between 5 and 50 characters")
    @Column(name = "name",length = 50, nullable = false)
    private String name;


    public TokenType(Long id) {
        this.id = id;
    }

}
