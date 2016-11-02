package myblog.domain;

import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.Updatable;
import myblog.exception.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

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

    /**
     *
     */
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

    /**
     *
     * @throws FieldNotInsertableException
     * @throws FieldNotNullableException
     */
    public void checkFieldInsertable()
            throws FieldNotInsertableException, FieldNotNullableException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new FieldNotInsertableException(e, field);
            }

            if (isInsertable(field)) {
                if (!isDefaultable(field)) {
                    if (!isNullable(field) && value == null) {
                        throw new FieldNotNullableException(field);
                    }
                }
            } else if (value != null) {
                throw new FieldNotInsertableException(field);
            }
        }
    }

    /**
     *
     * @throws FieldNotUpdatableException
     */
    public void checkFieldUpdatable() throws FieldNotUpdatableException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new FieldNotUpdatableException(e, field);
            }

            if (!isUpdatable(field) && value != null) {
                throw new FieldNotUpdatableException(field);
            }
        }
    }

    /**
     *
     * @throws FieldNotOuterSettableException
     */
    public void checkFieldOuterSettable() throws FieldNotOuterSettableException {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new FieldNotOuterSettableException(e, field);
            }

            if (!isOuterSettable(field) && value != null) {
                throw new FieldNotOuterSettableException(field);
            }
        }
    }

    /**
     *
     * @return
     * @throws DomainFieldException
     */
    public HashMap<String, Object> convertToHashMap(Field[] unless) throws DomainFieldException {
        List<Field> fields = Arrays.asList(getClass().getDeclaredFields());
        fields.removeIf(field -> {
            for (Field remove : unless) {
                if (field.getName().equals(remove.getName())) {
                    return true;
                }
            }

            return false;
        });

        HashMap<String, Object> params = new HashMap<String, Object>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                params.put(field.getName(), value);
            } catch (Exception e) {
                throw new DomainFieldException(e, field);
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
     * @throws DomainFieldException
     */
    public static <T extends Domain> T fromHashMap(Class<T> clazz, Map<String, Object> map)
            throws DomainException, DomainFieldException {
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
                        throw new DomainFieldException(e, field);
                    }
                }
            }
        }

        return instance;
    }
}
