package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotInsertableException extends DomainFieldException {

    public FieldNotInsertableException(Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not insertable", field);
    }

    public FieldNotInsertableException(Exception e, Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not insertable", e, field);
    }
}
