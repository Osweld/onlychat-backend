package german.dev.onlychatbackend.rol.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @NotBlank(message = "The rol is required")
    @Size(min = 4, max = 25, message = "The rol must be between 4 and 25 characters")
    @Column(name = "name",nullable = false, length = 25)
    private String name;
}
