package myblog.domain;

import myblog.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class Domain {

    /**
     * Check if all field is null
     *
     * @return
     */
    public boolean checkAllFieldsIsNull() {
        boolean isNull = true;
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                isNull = isNull && (value == null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return isNull;
    }

    /**
     * Check if all field is null except primary key
     *
     * @return
     */
    public boolean checkAllFieldsIsNullExceptPK() {
        boolean isNull = true;
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                if (!field.isAnnotationPresent(PrimaryKey.class)) {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    isNull = isNull && (value == null);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return isNull;
    }

    /**
     * Convert Object to HashMap
     *
     * @return
     */
    public HashMap<String, Object> convertToHashMap() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        for (Field field : this.getClass().getDeclaredFields()) {
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
