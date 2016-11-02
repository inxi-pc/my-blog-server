package myblog.exception;

import java.lang.reflect.Field;

public class DomainFieldException extends Exception {

    private Field field;

    public DomainFieldException(Throwable cause, Field field) {
        super(cause);

        this.field = field;
    }

    public DomainFieldException(String message, Field field) {
        super(message);

        this.field = field;
    }

    public DomainFieldException(String message, Throwable cause, Field field) {
        super(message + '\n' + cause.getMessage(), cause);

        this.field = field;
    }

    public Field getField() {
        return this.field;
    }

    public static String getFormattedFieldName(Field field) {
        return field.getName().replace("_", " ");
    }
}
