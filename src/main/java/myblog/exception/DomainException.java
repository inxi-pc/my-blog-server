package myblog.exception;

public class DomainException extends Exception {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(Exception e) {
        super(e);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
