package myblog.exception;

import java.lang.reflect.Field;
import java.util.Optional;

public class DomainException extends Exception {

    private Optional<Field> field;

    private Optional<Class> clazz;

    private Optional<String> wrap;

    private static MessageFactory<Type> messageFactory = new MessageFactory<Type>();

    private Type type;

    public enum Type implements Message {

        FIELD_NOT_INSERTABLE("Unexpected %s: Not insertabled"),

        FIELD_NOT_NULLABLE("Unexpected %s: Not nullable"),

        FIELD_NOT_OUTER_SETTABLE("Unexpected %s: Not outer settable"),

        FIELD_NOT_UPDATABLE("Unexpected %s: Not updatable"),

        FIELD_NOT_VALID_VALUE("Unexpected %s: Not valid value"),

        ILLEGAL_NUMBER_OF_IDENTIFIER("Illegal number of identifier"),

        ILLEGAL_NUMBER_OF_PASSWORD("Illegal number of password"),

        CATEGORY_CHILDREN_HAS_NO_PARENT("Category children has no parent");

        private String format;

        Type(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }

    public DomainException(Throwable cause) {
        super(cause);
    }

    public DomainException(Field field, Type type) {
        super(messageFactory.getFormattedMessage(field, type));

        this.type = type;
        this.field = Optional.of(field);
        this.clazz = Optional.empty();
    }

    public DomainException(Class clazz, Type type) {
        super(messageFactory.getFormattedMessage(clazz, type));

        this.type = type;
        this.clazz = Optional.of(clazz);
        this.field = Optional.empty();
    }

    public DomainException(String wrap, Type type) {
        super(messageFactory.getFormattedMessage(wrap, type));

        this.type = type;
        this.field = Optional.empty();
        this.clazz = Optional.empty();
        this.wrap = Optional.of(wrap);
    }

    public DomainException(Type type) {
        super(messageFactory.getFormattedMessage(type));

        this.type = type;
        this.clazz = Optional.empty();
        this.field = Optional.empty();
    }

    public Optional<Field> getField() {
        return this.field;
    }

    public Optional<Class> getClazz() {
        return this.clazz;
    }

    public Optional<String> getWrap() {
        return this.wrap;
    }

    public Type getType() {
        return this.type;
    }
}
