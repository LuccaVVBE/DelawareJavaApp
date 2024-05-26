package exceptions;

public class InvalidPriceException extends Exception {

    public InvalidPriceException() {
        super("Price must be greater than or equal to 0");
    }
}
