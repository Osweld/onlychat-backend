package german.dev.onlychatbackend.rol.exception;

import german.dev.onlychatbackend.common.exception.NotFoundException;

public class RolNotFoundException extends NotFoundException {

    public RolNotFoundException(String message) {
        super(message);
    }

}
