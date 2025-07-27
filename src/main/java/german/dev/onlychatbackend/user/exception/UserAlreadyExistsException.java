package german.dev.onlychatbackend.user.exception;

import german.dev.onlychatbackend.common.exception.AlreadyExistsException;

public class UserAlreadyExistsException  extends AlreadyExistsException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
