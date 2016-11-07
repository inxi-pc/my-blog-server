package myblog.exception;

import java.lang.reflect.Field;
import java.util.Optional;

public class DomainException extends Exception {

    private Optional<Field> field;

    private Optional<Class> clazz;

    private Type type;

    public static enum Type {

        FIELD_NOT_INSERTABLE("Unexpected %s: Not insertabled"),

        FIELD_NOT_NULLABLE("Unexpected %s: Not nullable"),

        FIELD_NOT_OUTER_SETTABLE("Unexpected %s: Not outer settable"),

        FIELD_NOT_UPDATABLE("Unexpected %s: Not updatable"),

        USER_ILLEGAL_NUMBER_OF_IDENTIFIER("User illegal number of identifier"),

        USER_ILLEGAL_NUMBER_OF_PASSWORD("User illegal number of password"),

        CATEGORY_CHILDREN_HAS_NO_PARENT("Category children has no parent");

        private String format;

        private Type(String format) {
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
        super(getFormattedMessage(field, type));

        this.type = type;
        this.field = Optional.of(field);
        this.clazz = Optional.empty();
    }

    public DomainException(Class clazz, Type type) {
        super(getFormattedMessage(clazz, type));

        this.type = type;
        this.clazz = Optional.of(clazz);
        this.field = Optional.empty();
    }

    public DomainException(Type type) {
        super(getFormattedMessage(type));

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

    public Type getType() {
        return this.type;
    }


    private static String getFormattedMessage(Type type) {
        return type.format;
    }

    private static String getFormattedMessage(Class clazz, Type type) {
        return String.format(type.getFormat(), clazz.getName());
    }

    private static String getFormattedMessage(Field field, Type type) {
        return String.format(type.getFormat(), field.getName().replace("_", " "));
    }
}
