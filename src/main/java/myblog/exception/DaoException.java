package myblog.exception;

import java.util.Optional;

public class DaoException extends Exception {

    private Type type;

    private Optional<Class> clazz;

    public static enum Type {

        EMPTY_VALUE("Unexpected %s: Empty value"),

        NULL_POINTER("Unexpected %s: Null pointer"),

        INVALID_PARAM("Unexpected %s: Invalid parameter");

        private String format;

        private Type(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(Class clazz, Type type) {
        super(getFormattedMessage(clazz, type));

        this.type = type;
        this.clazz = Optional.of(clazz);
    }

    public DaoException(Type type) {
        super(getFormattedMessage(type));

        this.type = type;
        this.clazz = Optional.empty();
    }

    public Optional<Class> getClazz() {
        return this.clazz;
    }

    public Type getType() {
        return this.type;
    }

    private static String getFormattedMessage(Type type) {
        return type.getFormat();
    }

    private static String getFormattedMessage(Class clazz, Type type) {
        return String.format(type.getFormat(), clazz.getName());
    }
}
