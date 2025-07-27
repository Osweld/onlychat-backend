package german.dev.onlychatbackend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequestDTO {


    @NotBlank(message = "The password is required")
    @Size(min=8, max = 20, message = "The password must be between 8 and 20 characters")
    private String password;

}
