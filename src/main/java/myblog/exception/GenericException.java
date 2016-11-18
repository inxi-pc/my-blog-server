package myblog.exception;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;

public class GenericException extends RuntimeException {

    protected Response.Status status;

    protected MessageMeta meta;

    protected enum DefaultMessageMeta implements MessageMeta {

        None("");

        private String format;

        DefaultMessageMeta(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }

    public GenericException(Throwable cause) {
        super(cause);

        this.meta = DefaultMessageMeta.None;
        this.status = Response.Status.INTERNAL_SERVER_ERROR;
    }

    public GenericException(MessageMeta meta, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta));

        this.meta = meta;
        this.status = status;
    }

    public GenericException(MessageMeta meta, Field field, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta, field));

        this.meta = meta;
        this.status = status;
    }

    public GenericException(MessageMeta meta, Class clazz, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta, clazz));

        this.meta = meta;
        this.status = status;
    }

    public GenericException(MessageMeta meta, String wrap, Response.Status status) {
        super(MessageFactory.getFormattedMessage(meta, wrap));

        this.meta = meta;
        this.status = status;
    }

    public MessageMeta getMeta() {
        return this.meta;
    }

    public Response.Status getStatus() {
        return this.status;
    }
}
