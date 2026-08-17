package osbjava.exceptions;

public class OutOfBoundsException extends RuntimeException {
    public OutOfBoundsException() {
        super("Indexing is out of bounds");
    }
}
