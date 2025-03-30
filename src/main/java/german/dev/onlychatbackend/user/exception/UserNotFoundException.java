package german.dev.onlychatbackend.user.exception;

import german.dev.onlychatbackend.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    
    public UserNotFoundException(String message) {
        super(message);
    }

}
