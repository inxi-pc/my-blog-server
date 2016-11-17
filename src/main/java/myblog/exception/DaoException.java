package myblog.exception;

import javax.ws.rs.core.Response;

public class DaoException extends GenericException {

    public enum Type implements MessageMeta {

        INSERT_NULL_OBJECT("Insert null object", Response.Status.BAD_REQUEST),

        INVALID_DELETED_ID("Invalid deleted id", Response.Status.BAD_REQUEST),

        INVALID_UPDATED_ID("Invalid updated id", Response.Status.BAD_REQUEST),

        INVALID_QUERY_ID("Invalid query id", Response.Status.BAD_REQUEST),

        NULL_QUERY_PARAM("Query param is null", Response.Status.BAD_REQUEST),

        EMPTY_QUERY_PARAM("Query param is empty", Response.Status.BAD_REQUEST);

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

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(Type type) {
        super(type);
    }
}
