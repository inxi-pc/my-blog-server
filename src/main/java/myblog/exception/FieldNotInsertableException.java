package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotInsertableException extends DomainFieldException {

    public FieldNotInsertableException(Field field) {
        super("Unexpected "
                + field.getName().replace("_", " ")
                + ": " + "Not insertable", field);
    }
}
