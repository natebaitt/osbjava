package osbjava.exceptions;

public class ColorException extends RuntimeException {
    public ColorException() {
        super("Color parameter is invalid or out of its expected range");
    }
}
