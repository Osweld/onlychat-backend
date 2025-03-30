package german.dev.onlychatbackend.common.exception;



public class AccountNotActivatedException extends RuntimeException {
    
    public AccountNotActivatedException(String message) {
        super(message);
    }
    
    public AccountNotActivatedException(String message, Throwable cause) {
        super(message, cause);
    }
      
}