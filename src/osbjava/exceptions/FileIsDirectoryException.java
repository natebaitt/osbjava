package osbjava.exceptions;

public class FileIsDirectoryException extends RuntimeException {
    public FileIsDirectoryException() {
        super("File to export is a directory");
    }
}
