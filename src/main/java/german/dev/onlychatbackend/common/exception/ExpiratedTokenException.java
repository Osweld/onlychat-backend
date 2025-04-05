package german.dev.onlychatbackend.common.exception;

import org.springframework.security.authentication.DisabledException;

public class ExpiratedTokenException extends DisabledException{

    public ExpiratedTokenException(String message) {
        super(message);
        
    }

    
    

}
