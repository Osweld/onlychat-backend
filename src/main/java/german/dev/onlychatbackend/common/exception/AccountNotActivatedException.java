package german.dev.onlychatbackend.common.exception;

import org.springframework.security.authentication.DisabledException;

public class AccountNotActivatedException extends DisabledException {
    
    public AccountNotActivatedException(String message) {
        super(message);
    }
    
}