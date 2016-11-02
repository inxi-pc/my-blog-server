package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotOuterSettableException extends DomainFieldException {

    public FieldNotOuterSettableException(Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not outer settable", field);
    }

    public FieldNotOuterSettableException(Exception e, Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not outer settable", e, field);
    }
}
