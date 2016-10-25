package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotUpdatableException extends DomainFieldException {

    public FieldNotUpdatableException(Field field) {
        super(field);
    }

    @Override
    public String getMessage() {
        return "Unexpected "
                + this.getField().getName().replace("_", " ")
                + ": " + "Not updatable";
    }
}
