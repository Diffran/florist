package florist.exceptions;

public class EmptyStringException extends Exception{
    public EmptyStringException(String message) {
        super("ERROR: empty String - "+message);
    }

}
