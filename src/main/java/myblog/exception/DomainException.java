package myblog.exception;

import java.lang.reflect.Field;

public class DomainException extends Exception {

    private Class clazz;

    private Type type;

    private Field field;

    public static enum Type {

        FIELD_NOT_INSERTABLE("Unexpected %s: Not insertabled"),

        FIELD_NOT_NULLABLE("Unexpected %s: Not nullable"),

        FIELD_NOT_OUTER_SETTABLE("Unexpected %s: Not outer settable"),

        FIELD_NOT_UPDATABLE("Unexpected %s: Not updatable"),

        ILLEGAL_NUMBER_OF_IDENTIFIER("Unexpected %s: Illegal number of identifier"),

        ILLEGAL_NUMBER_OF_PASSWORD("Unexpected %s: Illegal number of password");

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
        this.field = field;
    }

    public DomainException(Class clazz, Type type) {
        super(getFormattedMessage(clazz, type));

        this.type = type;
        this.clazz = clazz;
    }

    public Field getField() {
        return this.field;
    }

    public Class getClazz() {
        return this.clazz;
    }

    public Type getType() {
        return this.type;
    }

    private static String getFormattedMessage(Class clazz, Type type) {
        return String.format(type.getFormat(), clazz.getName());
    }

    private static String getFormattedMessage(Field field, Type type) {
        return String.format(type.getFormat(), field.getName().replace("_", " "));
    }
}
