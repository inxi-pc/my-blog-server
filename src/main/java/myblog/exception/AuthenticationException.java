package myblog.exception;

public class AuthenticationException extends Exception {

    private static final long serialVersionUID = -5053567474138953905L;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}

