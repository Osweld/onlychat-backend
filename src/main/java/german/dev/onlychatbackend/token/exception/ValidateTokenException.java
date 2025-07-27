package german.dev.onlychatbackend.token.exception;

public class ValidateTokenException extends RuntimeException {

    public ValidateTokenException(String message) {
        super(message);
    }

    public ValidateTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
