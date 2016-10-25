package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotOuterSettableException extends DomainFieldException {

    public FieldNotOuterSettableException(Field field) {
        super("Unexpected "
                + field.getName().replace("_", " ")
                + ": " + "Not outer settable", field);
    }
}
