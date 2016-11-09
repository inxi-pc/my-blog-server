package myblog.exception;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import java.lang.reflect.Constructor;

public class HttpExceptionFactory {

    public static enum Type {

        UNEXPECTED("Unexpected %s: "),

        CREATE_FAILED("%s create failed: "),

        DELETE_FAILED("%s delete failed: "),

        UPDATE_FAILED("%s update failed: "),

        NOT_FOUND("Not found %s: "),

        CONFLICT("Conflict %s: ");

        private String format;

        private Type(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }

    public static enum Reason {

        UNDEFINED_ERROR("Undefined error"),

        ABSENCE_VALUE("Absence value"),

        INVALID_VALUE("Invalid value"),

        INVALID_PRIMARY_KEY_VALUE("Invalid primary key value"),

        INVALID_CREDENTIAL("Invalid identifier or password"),

        NOT_EXIST("Not exist"),

        NOT_EXIST_CATEGORY_PARENT("Not exist category parent"),

        NOT_ELIGIBLE("Not eligible"),

        EXIST_ALREADY("Exist already");

        private String detail;

        private Reason(String detail) {
            this.detail = detail;
        }

        public String getDetail() {
            return this.detail;
        }
    }

    public static<T extends WebApplicationException> T produce(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    public static<T extends WebApplicationException> T produce(Class<T> clazz,
                                                               Type type,
                                                               Class wrap,
                                                               Reason reason) {
        String message = getFormattedMessage(type, wrap) + reason.getDetail();

        return produce(clazz, message);
    }

    public static<T extends WebApplicationException> T produce(Class<T> clazz,
                                                               Type type,
                                                               String wrap,
                                                               Reason reason) {
        String message = getFormattedMessage(type, wrap) + reason.getDetail();

        return produce(clazz, message);
    }

    public static<T extends WebApplicationException> T produce(Class<T> clazz,
                                                               String message) {
        try {
            Constructor method = clazz.getConstructor(String.class);

            return (T) method.newInstance(message);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    public static<T extends WebApplicationException> T produce(Class<T> clazz,
                                                               Exception e) {
        try {
            Constructor method = clazz.getConstructor(String.class, Throwable.class);

            return (T) method.newInstance(e.getMessage(), e);
        } catch (Exception ex) {
            throw new InternalServerErrorException(e.getMessage(), ex);
        }
    }

    private static String getFormattedMessage(Type type, Class wrap) {
        return String.format(type.getFormat(), wrap.getName());
    }

    private static String getFormattedMessage(Type type, String name) {
        return String.format(type.getFormat(), name);
    }
}
