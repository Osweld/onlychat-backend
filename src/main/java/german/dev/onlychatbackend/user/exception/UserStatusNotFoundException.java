package german.dev.onlychatbackend.user.exception;

import german.dev.onlychatbackend.common.exception.NotFoundException;

public class UserStatusNotFoundException extends NotFoundException {
    
    public UserStatusNotFoundException(String message) {
        super(message);
    }

}
