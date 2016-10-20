package myblog.domain;

import myblog.annotation.Insertable;
import myblog.annotation.Updateable;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class Domain {

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

            if (field.isAnnotationPresent(Insertable.class)) {
                Insertable insertable = field.getDeclaredAnnotation(Insertable.class);
                if (!insertable.nullable() && value == null) {
                    return field;
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

            if (!field.isAnnotationPresent(Updateable.class)) {
                if (value != null) {
                    return field;
                }
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
