package myblog.exception;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;

public class DomainException extends GenericException {

    public enum Type implements MessageMeta {

        FIELD_NOT_INSERTABLE("Unexpected %s: Not insertabled", Response.Status.BAD_REQUEST),

        FIELD_NOT_NULLABLE("Unexpected %s: Not nullable", Response.Status.BAD_REQUEST),

        FIELD_NOT_OUTER_SETTABLE("Unexpected %s: Not outer settable", Response.Status.BAD_REQUEST),

        FIELD_NOT_UPDATABLE("Unexpected %s: Not updatable", Response.Status.BAD_REQUEST),

        FIELD_NOT_VALID_VALUE("Unexpected %s: Not valid value", Response.Status.BAD_REQUEST),

        OBJECT_NOT_EXIST("Not exist %s", Response.Status.NOT_FOUND),

        ILLEGAL_NUMBER_OF_IDENTIFIER("Illegal number of identifier", Response.Status.BAD_REQUEST),

        ILLEGAL_NUMBER_OF_PASSWORD("Illegal number of password", Response.Status.BAD_REQUEST),

        CATEGORY_TREE_INVALID("Invalid category tree, some category has no parent", Response.Status.INTERNAL_SERVER_ERROR),

        CATEGORY_PARENT_NOT_EXIST("Category parent not exist", Response.Status.BAD_REQUEST);

        private String format;

        private Response.Status status;

        Type(String format, Response.Status status) {
            this.format = format;
            this.status = status;
        }

        public String getFormat() {
            return this.format;
        }

        public Response.Status getStatus() {
            return this.status;
        }
    }

    public DomainException(Throwable cause) {
        super(cause);
    }

    public DomainException(Type type) {
        super(type);
    }

    public DomainException(Type type, Field field) {
        super(field, type);
    }

    public DomainException(Type type, Class clazz) {
        super(clazz, type);
    }
}
