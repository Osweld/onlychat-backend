package german.dev.onlychatbackend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequestDTO {

    @NotBlank(message = "The old password is required")
    @Size(min=8, max = 20, message = "The old password must be between 8 and 20 characters")
    private String oldPassword;
    @NotBlank(message = "The new password is required")
    @Size(min=8, max = 20, message = "The new password must be between 8 and 20 characters")
    private String newPassword;  
}
