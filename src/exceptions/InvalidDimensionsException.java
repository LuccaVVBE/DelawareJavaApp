package exceptions;

public class InvalidDimensionsException extends Exception {
    public InvalidDimensionsException() {
        super("Dimensions should be greater than 0.");
    }
}
