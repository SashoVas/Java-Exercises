package flightscanner.exception;

public class InvalidFlightException extends RuntimeException{
    public InvalidFlightException(String message) {
        super(message);
    }

    public InvalidFlightException(String message, Throwable cause) {
        super(message, cause);
    }
}
