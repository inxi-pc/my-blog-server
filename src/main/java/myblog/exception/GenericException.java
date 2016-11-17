package myblog.exception;

import java.lang.reflect.Field;
import java.util.Optional;

public class GenericException extends RuntimeException {

    protected Optional<Field> field;

    protected Optional<Class> clazz;

    protected MessageMeta meta;

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(MessageMeta meta) {
        super(MessageFactory.getFormattedMessage(meta));

        this.meta = meta;
        this.clazz = Optional.empty();
        this.field = Optional.empty();
    }

    public GenericException(Field field, MessageMeta meta) {
        super(MessageFactory.getFormattedMessage(meta, field));

        this.meta = meta;
        this.field = Optional.of(field);
        this.clazz = Optional.empty();
    }

    public GenericException(Class clazz, MessageMeta meta) {
        super(MessageFactory.getFormattedMessage(meta, clazz));

        this.meta = meta;
        this.clazz = Optional.of(clazz);
        this.field = Optional.empty();
    }

    public Optional<Field> getField() {
        return this.field;
    }

    public Optional<Class> getClazz() {
        return this.clazz;
    }

    public MessageMeta getMeta() {
        return this.meta;
    }
}
