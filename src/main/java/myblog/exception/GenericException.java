package myblog.exception;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;

public class GenericException extends RuntimeException {

    private Response.Status status;

    public GenericException(Throwable cause) {
        super(cause);

        this.status = Response.Status.INTERNAL_SERVER_ERROR;
    }

    public GenericException(MessageMeta meta, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta));

        this.status = status;
    }

    public GenericException(MessageMeta meta, Field field, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta, field));

        this.status = status;
    }

    public GenericException(MessageMeta meta, Class clazz, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta, clazz));

        this.status = status;
    }

    public GenericException(MessageMeta meta, String wrap, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta, wrap));

        this.status = status;
    }

    public Response.Status getStatus() {
        return this.status;
    }
}
