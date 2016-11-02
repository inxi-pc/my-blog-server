package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotNullableException extends DomainFieldException {

    public FieldNotNullableException(Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not nullable", field);
    }

    public FieldNotNullableException(Exception e, Field field) {
        super("Unexpected "
                + getFormattedFieldName(field)
                + ": " + "Not nullable", e, field);
    }
}