package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotOuterSettableException extends DomainFieldException {

    public FieldNotOuterSettableException(Field field) {
        super(field);
    }

    @Override
    public String getMessage() {
        return "Unexpected "
                + this.getField().getName().replace("_", " ")
                + ": " + "Not updatable";
    }
}
