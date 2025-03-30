package german.dev.onlychatbackend.chat.exception;

import german.dev.onlychatbackend.common.exception.ChatException;

public class MessageNotFoundException extends ChatException {

    public MessageNotFoundException(String message) {
        super(message);
    }

    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
