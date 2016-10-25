package myblog.exception;

import java.lang.reflect.Field;

public class DomainFieldException extends Exception {

    private Field field;

    public DomainFieldException(String message, Field field) {
        super(message);
        this.field = field;
    }

    public Field getField() {
        return this.field;
    }
}
