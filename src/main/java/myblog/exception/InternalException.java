package myblog.exception;

public class InternalException extends ExtendException {

    public InternalException(String message) {
        super(500, message);
    }

    public InternalException(Exception e) {
        super(500, e);
    }
}
