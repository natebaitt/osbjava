package osbjava.exceptions;

public class ParameterRangeException extends RuntimeException {
    public ParameterRangeException() {
        super("Command parameter is out of its expected range");
    }
}
