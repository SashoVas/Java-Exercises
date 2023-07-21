package flightscanner.exception;

public class FlightCapacityExceededException extends Exception{
    public FlightCapacityExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlightCapacityExceededException(String message) {
        super(message);
    }
}
