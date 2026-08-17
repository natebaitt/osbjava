package osbjava.exceptions;

public class FileIOException extends RuntimeException {
    public FileIOException() {
        super("Something went wrong when opening or writing the file");
    }
}
