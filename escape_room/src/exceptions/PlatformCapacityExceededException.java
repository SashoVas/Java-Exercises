package exceptions;

public class PlatformCapacityExceededException extends RuntimeException{
    public PlatformCapacityExceededException(String message) {
        super(message);
    }

    public PlatformCapacityExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
