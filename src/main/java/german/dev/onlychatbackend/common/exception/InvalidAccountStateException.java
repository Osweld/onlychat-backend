package german.dev.onlychatbackend.common.exception;

public class InvalidAccountStateException extends RuntimeException {
    public InvalidAccountStateException(String message) {
        super(message);
    }
}
