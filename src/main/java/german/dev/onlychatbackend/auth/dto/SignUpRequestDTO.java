package german.dev.onlychatbackend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpRequestDTO {
    
@NotBlank(message = "The username is required")
    @Size(min=5, max = 20, message = "The username must be between 5 and 20 characters")
    private String username;

    @NotBlank(message = "The password is required")
    @Size(min=8, max = 20, message = "The password must be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "The email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email format")
    private String email;
}


