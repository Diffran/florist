package florist.exceptions;

public class InvalidDecorationType extends Exception{
    public InvalidDecorationType() {
        super("Invalid decoration type. Please enter wood or plastic: ");
    }
}
