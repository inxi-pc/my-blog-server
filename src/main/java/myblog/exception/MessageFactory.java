package myblog.exception;

import java.lang.reflect.Field;

public class MessageFactory {

    public static String getFormattedMessage(MessageMeta meta) {
        if (!meta.hasPlaceholder()) {
            return meta.getFormat();
        } else {
            throw new IllegalArgumentException("Require literal meta but got generic meta");
        }
    }

    public static String getFormattedMessage(MessageMeta meta, String wrap) {
        if (meta.hasPlaceholder()) {
            return String.format(meta.getFormat(), wrap);
        } else {
            throw new IllegalArgumentException("Require generic meta but got literal meta");
        }
    }

    public static String getFormattedMessage(MessageMeta meta, Class clazz) {
        if (meta.hasPlaceholder()) {
            return String.format(meta.getFormat(), clazz.getName());
        } else {
            throw new IllegalArgumentException("Require generic meta but got literal meta");
        }
    }

    public static String getFormattedMessage(MessageMeta meta, Field field) {
        if (meta.hasPlaceholder()) {
            return String.format(meta.getFormat(), field.getName());
        } else {
            throw new IllegalArgumentException("Require generic meta but got literal meta");
        }
    }
}
