package german.dev.onlychatbackend.auth.dto;

import java.util.Date;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private Long id;
    private String token;
    private String username;
    private Date expirationDate;
}
