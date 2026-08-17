package osbjava.exceptions;

public class FileDoesNotExistException extends RuntimeException {
    public FileDoesNotExistException() {
        super("File does not exist at the given filepath");
    }
}
