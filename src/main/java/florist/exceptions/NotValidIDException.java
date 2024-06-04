package florist.exceptions;

public class NotValidIDException extends Exception{
    public NotValidIDException(String message) {
        super("Error: "+message);
    }
}
