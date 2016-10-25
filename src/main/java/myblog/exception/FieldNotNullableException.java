package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotNullableException extends DomainFieldException {

    public FieldNotNullableException(Field field) {
        super(field);
    }

    @Override
    public String getMessage() {
        return "Unexpected "
                + this.getField().getName().replace("_", " ")
                + ": " + "Not Nullable";
    }
}