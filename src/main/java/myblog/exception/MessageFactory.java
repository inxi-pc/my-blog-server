package myblog.exception;

import java.lang.reflect.Field;

public class MessageFactory<T extends Message> {

    public String getFormattedMessage(T type) {
        return type.getFormat();
    }

    public String getFormattedMessage(Class clazz, T type) {
        return String.format(type.getFormat(), clazz.getName());
    }

    public String getFormattedMessage(String wrap, T type) {
        return String.format(type.getFormat(), wrap);
    }

    public String getFormattedMessage(Field field, T type) {
        return String.format(type.getFormat(), field.getName().replace("_", " "));
    }

    public String getFormattedMessage(Class clazz, String fieldName, T type) {
        try {
            Field field = clazz.getDeclaredField(fieldName);

            return getFormattedMessage(field, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
