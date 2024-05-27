package florist.exceptions;

public class EmptySQLTableException extends Exception{
    public EmptySQLTableException(String message) {
        super("no element found in: "+ message);
    }
}
