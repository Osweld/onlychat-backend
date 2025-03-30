package german.dev.onlychatbackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResendActivateAccountTokenResponseDTO {

    private String email;

}
