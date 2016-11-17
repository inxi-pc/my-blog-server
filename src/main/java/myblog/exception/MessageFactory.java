package myblog.exception;

import java.lang.reflect.Field;

public class MessageFactory {

    public static String getFormattedMessage(MessageMeta meta) {
        return meta.getFormat();
    }

    public static String getFormattedMessage(MessageMeta meta, String wrap) {
        return String.format(meta.getFormat(), wrap);
    }

    public static String getFormattedMessage(MessageMeta meta, Class clazz) {
        return String.format(meta.getFormat(), clazz.getName());
    }

    public static String getFormattedMessage(MessageMeta meta, Field field) {
        return String.format(meta.getFormat(), field.getName().replace("_", " "));
    }
}
