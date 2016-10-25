package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotNullableException extends DomainFieldException {

    public FieldNotNullableException(Field field) {
        super("Unexpected "
                + field.getName().replace("_", " ")
                + ": " + "Not nullable", field);
    }
}