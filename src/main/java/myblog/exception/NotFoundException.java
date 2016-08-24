package myblog.exception;

public class NotFoundException extends ExtendException {

    public NotFoundException(String message) {
        super(404, message);
    }

    public NotFoundException(Exception e) {
        super(404, e);
    }
}
