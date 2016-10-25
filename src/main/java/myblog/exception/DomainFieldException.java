package myblog.exception;

import java.lang.reflect.Field;

public class DomainFieldException extends Exception {

    private Field field;

    public DomainFieldException(Field field) {
        super();
        this.field = field;
    }

    public Field getField() {
        return this.field;
    }
}
