package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotUpdatableException extends DomainFieldException {

    public FieldNotUpdatableException(Field field) {
        super("Unexpected "
                + field.getName().replace("_", " ")
                + ": " + "Not updatable", field);
    }
}
