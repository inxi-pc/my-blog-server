package myblog.domain;

import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.Updatable;
import myblog.exception.FieldNotInsertableException;
import myblog.exception.FieldNotNullableException;
import myblog.exception.FieldNotOuterSettableException;
import myblog.exception.FieldNotUpdatableException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class Domain {

    /**
     *
     * @param field
     * @return
     */
    private static boolean isUpdatable(Field field) {
        return field.isAnnotationPresent(Updatable.class);
    }

    /**
     *
     * @param field
     * @return
     */
    private static boolean isInsertable(Field field) {
        return field.isAnnotationPresent(Insertable.class);
    }

    /**
     *
     * @param field
     * @return
     */
    private static boolean isOuterSettable(Field field) {
        return field.isAnnotationPresent(OuterSettable.class);
    }
    /**
     *
     * @param field
     * @return
     */
    private static boolean isNullable(Field field) {
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
    private static boolean isDefaultable(Field field) {
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
    private static String getDefaultValueSetterName(Field field) {
        String fieldName = field.getName();
        String prefix = "setDefault";

        return  prefix + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1, fieldName.length());
    }

    public void setDefaultableFieldValue() {
        for (Field field : getClass().getDeclaredFields()) {
            if (isDefaultable(field)) {
                Object value;
                try {
                    field.setAccessible(true);
                    value = field.get(this);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (value == null) {
                    String setterName = getDefaultValueSetterName(field);
                    try {
                        Method method = getClass().getDeclaredMethod(setterName);
                        method.invoke(this);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void checkFieldInsertable() throws FieldNotInsertableException,
            FieldNotNullableException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (isInsertable(field)) {
                if (!isDefaultable(field)) {
                    if (!isNullable(field) && value == null) {
                        throw new FieldNotInsertableException(field);
                    }
                }
            } else if (value != null) {
                throw new FieldNotInsertableException(field);
            }
        }
    }

    public void checkFieldUpdatable() throws FieldNotUpdatableException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!isUpdatable(field) && value != null) {
                throw new FieldNotUpdatableException(field);
            }
        }
    }

    public void checkFieldOuterSettable() throws FieldNotOuterSettableException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!isOuterSettable(field) && value != null) {
                throw new FieldNotOuterSettableException(field);
            }
        }
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
