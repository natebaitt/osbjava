package osbjava.exceptions;

public class TimeRangeException extends RuntimeException {
    public TimeRangeException() {
        super("Command start time cannot be larger than end time");
    }
}
