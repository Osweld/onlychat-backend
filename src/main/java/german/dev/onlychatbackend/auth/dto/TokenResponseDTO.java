package german.dev.onlychatbackend.auth.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseDTO {

    private String token;
    private Date expirationDate;
}
