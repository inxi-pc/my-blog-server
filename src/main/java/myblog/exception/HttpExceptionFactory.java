package myblog.exception;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.lang.reflect.Constructor;

public class HttpExceptionFactory {

    /**
     * Exception message header
     */
    public enum Type {

        UNEXPECTED("Unexpected %s: "),

        CREATE_FAILED("%s create failed: "),

        DELETE_FAILED("%s delete failed: "),

        UPDATE_FAILED("%s update failed: "),

        NOT_FOUND("Not found %s: "),

        CONFLICT("Conflict %s: "),

        AUTHENTICATE_FAILED("Authenticate failed: ");

        private String format;

        Type(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }

    /**
     * Exception message body
     */
    public enum Reason {

        UNDEFINED_ERROR("Undefined error"),

        ABSENCE_VALUE("Absence value"),

        INVALID_VALUE("Invalid value"),

        INVALID_PRIMARY_KEY_VALUE("Invalid primary key value"),

        //---------------Query--------------------//
        NOT_EXIST("Not exist"),

        NOT_EXIST_PARENT_CATEGORY("Not exist parent category"),

        NOT_ELIGIBLE("Not eligible"),

        EXIST_ALREADY("Exist already"),

        //---------------Authenticate-------------//
        BEARER_TOKEN_EXPIRED("Credential expired"),

        INVALID_BEARER_TOKEN("Invalid bearer token"),

        INVALID_USERNAME_OR_PASSWORD("Invalid username or password");

        private String detail;

        Reason(String detail) {
            this.detail = detail;
        }

        public String getDetail() {
            return this.detail;
        }
    }

    /**
     * Produce exception by Class, message generate by Type + Reason
     *
     * @param clazz
     * @param type
     * @param reason
     * @param <T>
     * @return
     */
    public static<T extends WebApplicationException> T produce(Class<T> clazz,
                                                               Type type,
                                                               Reason reason) {
        String message = getFormattedMessage(type) + reason.getDetail();

        return produce(clazz, message);
    }

    /**
     * Produce exception by Class, message generate by Type + Reason
     *
     * @param clazz
     * @param type
     * @param wrap
     * @param reason
     * @param <T>
     * @return
     */
    public static<T extends WebApplicationException> T produce(Class<T> clazz,
                                                               Type type,
                                                               Class wrap,
                                                               Reason reason) {
        String message = getFormattedMessage(type, wrap) + reason.getDetail();

        return produce(clazz, message);
    }

    /**
     * Produce exception by Class, message generate by Type + Reason
     *
     * @param clazz
     * @param type
     * @param wrap
     * @param reason
     * @param <T>
     * @return
     */
    public static<T extends WebApplicationException> T produce(Class<T> clazz,
                                                               Type type,
                                                               String wrap,
                                                               Reason reason) {
        String message = getFormattedMessage(type, wrap) + reason.getDetail();

        return produce(clazz, message);
    }

    /**
     *
     * @param clazz
     * @param message
     * @param <T>
     * @return
     */
    private static<T extends WebApplicationException> T produce(Class<T> clazz, String message) {
        try {
            Constructor method = clazz.getDeclaredConstructor(String.class);

            return (T) method.newInstance(message);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex.getMessage(), ex);
        }
    }


    /**
     *
     * @param status
     * @param type
     * @param reason
     * @return
     */
    public static WebApplicationException produce(Response.Status status, Type type, Reason reason) {
        String message = getFormattedMessage(type) + reason.getDetail();

        return new WebApplicationException(message, status);
    }

    /**
     *
     * @param clazz
     * @param e
     * @param <T>
     * @return
     */
    public static<T extends WebApplicationException> T produce(Class<T> clazz, Exception e) {
        try {
            Constructor method = clazz.getDeclaredConstructor(String.class, Throwable.class);

            return (T) method.newInstance(e.getMessage(), e);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex.getMessage(), ex);
        }
    }

    private static String getFormattedMessage(Type type, Class wrap) {
        return String.format(type.getFormat(), wrap.getName());
    }

    private static String getFormattedMessage(Type type, String name) {
        return String.format(type.getFormat(), name);
    }

    private static String getFormattedMessage(Type type) {
        return type.getFormat();
    }
}
