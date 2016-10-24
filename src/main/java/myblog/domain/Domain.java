package myblog.domain;

import myblog.annotation.Insertable;
import myblog.annotation.Updatable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class Domain {

    /**
     *
     * @param field
     * @return
     */
    public static boolean isUpdatable(Field field) {
        return field.isAnnotationPresent(Updatable.class);
    }

    /**
     *
     * @param field
     * @return
     */
    public static boolean isInsertable(Field field) {
        return field.isAnnotationPresent(Insertable.class);
    }

    /**
     *
     * @param field
     * @return
     */
    public static boolean isNullable(Field field) {
        if (isInsertable(field)) {
            return field.getDeclaredAnnotation(Insertable.class).nullable();
        } else {
            return false;
        }
    }

    /**
     *
     * @param field
     * @return
     */
    public static boolean isDefaultable(Field field) {
        if (isInsertable(field)) {
            return field.getDeclaredAnnotation(Insertable.class).defaultable();
        } else {
            return false;
        }
    }

    /**
     *
     * @param field
     * @return
     */
    public static String getSetterMethodName(Field field) {
        String fieldName = field.getName();

        return  "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1, fieldName.length());
    }

    /**
     *
     * @param field
     */
    public void setDefaultValue(Field field) {
        String setterName = getSetterMethodName(field);
        try {
            Method method = getClass().getDeclaredMethod(setterName, field.getType());
            method.invoke(this, new Object[]{ null });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return
     */
    public Field checkInsertConstraint() {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (isInsertable(field)) {
                if (value == null) {
                    if (isDefaultable(field)) {
                        setDefaultValue(field);
                    } else if (!isNullable(field)) {
                        return field;
                    }
                }
            } else {
                if (value != null) {
                    return field;
                }
            }
        }

        return null;
    }

    /**
     *
     * @return
     */
    public Field checkUpdateConstraint() {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!isUpdatable(field) && value != null) {
                return field;
            }
        }

        return null;
    }

    /**
     * Convert Object to HashMap
     *
     * @return
     */
    public HashMap<String, Object> convertToHashMap() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        for (Field field : getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                params.put(field.getName(), value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return params;
    }
}
