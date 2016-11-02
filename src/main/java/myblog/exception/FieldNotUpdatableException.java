package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotUpdatableException extends DomainFieldException {

    public FieldNotUpdatableException(Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not updatable", field);
    }

    public FieldNotUpdatableException(Exception e, Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not updatable", e, field);
    }
}
