package exceptions;

public class NoAccessException extends Exception {
    public NoAccessException(String message) {
        super(message);
    }

    public NoAccessException() {
        super("No access to this page");
    }


}
