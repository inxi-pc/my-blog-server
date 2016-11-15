package myblog.exception;

import java.util.Optional;

public class DaoException extends Exception {

    private Optional<Class> clazz;

    private static MessageFactory<Type> messageFactory = new MessageFactory<Type>();

    private Type type;

    public enum Type implements Message {

        EMPTY_VALUE("Unexpected %s: Empty value"),

        NULL_POINTER("Unexpected %s: Null pointer"),

        INVALID_PARAM("Unexpected %s: Invalid parameter");

        private String format;

        Type(String format) {
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
        super(messageFactory.getFormattedMessage(clazz, type));

        this.type = type;
        this.clazz = Optional.of(clazz);
    }

    public DaoException(Type type) {
        super(messageFactory.getFormattedMessage(type));

        this.type = type;
        this.clazz = Optional.empty();
    }

    public Optional<Class> getClazz() {
        return this.clazz;
    }

    public Type getType() {
        return this.type;
    }
}
