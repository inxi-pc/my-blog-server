package myblog.exception;

import java.lang.reflect.Field;

public class FieldNotInsertableException extends DomainFieldException {

    public FieldNotInsertableException(Field field) {
        super(field);
    }

    @Override
    public String getMessage() {
        return "Unexpected "
                + this.getField().getName().replace("_", " ")
                + ": " + "Not insertable";
    }
}
