package myblog.exception;

public class IllegalNumberOfIdentifierException extends Exception {

    public IllegalNumberOfIdentifierException() {
        super("More than one or none");
    }

    public IllegalNumberOfIdentifierException(Throwable cause) {
        super("More than one or none", cause);
    }
}
