package icarius.services;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException (String message) {
        super(message);
    }
}
