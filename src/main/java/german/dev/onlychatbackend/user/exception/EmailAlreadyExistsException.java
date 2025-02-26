package german.dev.onlychatbackend.user.exception;

import german.dev.onlychatbackend.common.exception.AlreadyExistsException;

public class EmailAlreadyExistsException extends AlreadyExistsException{
    
    public EmailAlreadyExistsException(String message) {
       super(message);
    }



}
