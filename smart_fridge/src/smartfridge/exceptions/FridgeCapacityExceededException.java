package smartfridge.exceptions;

public class FridgeCapacityExceededException extends Exception{
    public FridgeCapacityExceededException(String message) {
        super(message);
    }

    public FridgeCapacityExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
