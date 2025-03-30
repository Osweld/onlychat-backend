package german.dev.onlychatbackend.common.exception;



public class ExpiratedTokenException extends RuntimeException {

    public ExpiratedTokenException(String message) {
        super(message);
        
    }
    
    public ExpiratedTokenException(String message, Throwable cause) {
        super(message, cause);
        
    }

    
    

}
