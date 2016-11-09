package myblog.domain;

import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.Updatable;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return isInsertable(field) && field.getDeclaredAnnotation(Insertable.class).nullable();
    }

    /**
     *
     * @param field
     * @return
     */
    private static boolean isDefaultable(Field field) {
        return isInsertable(field) && field.getDeclaredAnnotation(Insertable.class).defaultable();
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

    /**
     *
     * @throws DaoException
     */
    public void setDefaultableFieldValue() throws DaoException {
        for (Field field : getClass().getDeclaredFields()) {
            if (isDefaultable(field)) {
                Object value;
                try {
                    field.setAccessible(true);
                    value = field.get(this);
                } catch (Exception e) {
                    throw new DaoException(e);
                }

                if (value == null) {
                    String setterName = getDefaultValueSetterName(field);
                    try {
                        Method method = getClass().getDeclaredMethod(setterName);
                        method.invoke(this);
                    } catch (Exception e) {
                        throw new DaoException(e);
                    }
                }
            }
        }
    }

    /**
     *
     * @throws DomainException
     */
    public void checkFieldInsertable() throws DomainException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new DomainException(e);
            }

            if (isInsertable(field)) {
                if (!isDefaultable(field)) {
                    if (!isNullable(field) && value == null) {
                        throw new DomainException(field, DomainException.Type.FIELD_NOT_NULLABLE);
                    }
                }
            } else if (value != null) {
                throw new DomainException(field, DomainException.Type.FIELD_NOT_INSERTABLE);
            }
        }
    }

    /**
     *
     * @throws DomainException
     */
    public void checkFieldUpdatable() throws DomainException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new DomainException(e);
            }

            if (!isUpdatable(field) && value != null) {
                throw new DomainException(field, DomainException.Type.FIELD_NOT_UPDATABLE);
            }
        }
    }

    /**
     *
     * @throws DomainException
     */
    public void checkFieldOuterSettable() throws DomainException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new DomainException(e);
            }

            if (!isOuterSettable(field) && value != null) {
                throw new DomainException(field, DomainException.Type.FIELD_NOT_OUTER_SETTABLE);
            }
        }
    }

    /**
     *
     * @return
     * @throws DomainException
     */
    public HashMap<String, Object> convertToHashMap(Field[] unless) throws DomainException {
        List<Field> fields = Arrays.asList(getClass().getDeclaredFields());
        if (unless != null) {
            fields.removeIf(field -> {
                for (Field remove : unless) {
                    if (field.getName().equals(remove.getName())) {
                        return true;
                    }
                }

                return false;
            });
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                params.put(field.getName(), value);
            } catch (Exception e) {
                throw new DomainException(e);
            }
        }

        return params;
    }

    /**
     *
     * @param clazz
     * @param map
     * @param <T>
     * @return
     * @throws DomainException
     * @throws DomainException
     */
    public static <T extends Domain> T fromHashMap(Class<T> clazz, Map<String, Object> map)
            throws DomainException {
        T instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw new DomainException(e);
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            for (final Field field : instance.getClass().getDeclaredFields()) {
                if (field.getName().equals(entry.getKey())) {
                    field.setAccessible(true);

                    try {
                        field.set(instance, entry.getValue());
                    } catch (Exception e) {
                        throw new DomainException(e);
                    }
                }
            }
        }

        return instance;
    }
}
