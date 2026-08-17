package osbjava.exceptions;

public class DynamicTimeRangeException extends RuntimeException {
    public DynamicTimeRangeException() {
        super("Cannot add negative length to an dynamic object's lifetime");
    }
}
