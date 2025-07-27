package german.dev.onlychatbackend.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePersonalChatRequestDTO {

    @NotBlank(message = "The username is required")
    @Size(min=5, max = 20, message = "The username must be between 5 and 20 characters")
    private String username; 

}
