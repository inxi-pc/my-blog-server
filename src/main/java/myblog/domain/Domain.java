package myblog.domain;

import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.PrimaryKey;
import myblog.annotation.Updatable;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Domain {

	private enum SetterPrefix {

		DEFAULT_SETTER("setDefault"),

		BEAN_SETTER("set");

		private String prefix;

		SetterPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getPrefix() {
			return prefix;
		}
	}

    private static boolean isUpdatable(Field field) {
        return field.isAnnotationPresent(Updatable.class);
    }

    private static boolean isInsertable(Field field) {
        return field.isAnnotationPresent(Insertable.class);
    }

    private static boolean isOuterSettable(Field field) {
        return field.isAnnotationPresent(OuterSettable.class);
    }

    private static boolean isNullable(Field field) {
        return isInsertable(field) && field.getDeclaredAnnotation(Insertable.class).nullable();
    }

    private static boolean isDefaultable(Field field) {
        return isInsertable(field) && field.getDeclaredAnnotation(Insertable.class).defaultable();
    }

    private static String getSetterName(SetterPrefix prefix, Field field) {
        String fieldName = field.getName();

        return prefix.getPrefix() + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1, fieldName.length());
    }

    public void setDefaultableFieldValue() {
		Arrays.stream(getClass().getDeclaredFields()).forEach((Field field) -> {
			if (isDefaultable(field)) {
				Object value;
				try {
					field.setAccessible(true);
					value = field.get(this);
				} catch (Exception e) {
					throw new GenericException(e);
				}

				if (value == null) {
					String setterName = getSetterName(SetterPrefix.DEFAULT_SETTER, field);
					try {
						Method method = getClass().getDeclaredMethod(setterName);
						method.invoke(this);
					} catch (Exception e) {
						throw new GenericException(e);
					}
				}
			}
		});
    }

    public void checkFieldInsertable() {
		Arrays.stream(getClass().getDeclaredFields()).forEach((Field field) -> {
			Object value;
			try {
				field.setAccessible(true);
				value = field.get(this);
			} catch (Exception e) {
				throw new GenericException(e);
			}

			if (isInsertable(field)) {
				if (!isDefaultable(field)) {
					if (!isNullable(field) && value == null) {
						throw new GenericException(GenericMessageMeta.NOT_NULLABLE_FIELD, field, Response.Status.BAD_REQUEST);
					}
				}
			} else if (value != null) {
				throw new GenericException(GenericMessageMeta.NOT_INSERTABLE_FIELD, field, Response.Status.BAD_REQUEST);
			}
		});
    }

    public void checkFieldUpdatable() {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new GenericException(e);
            }

            if (!isUpdatable(field) && value != null) {
                throw new GenericException(GenericMessageMeta.NOT_UPDATABLE_FIELD, field, Response.Status.BAD_REQUEST);
            }
        }
    }

    public void checkFieldOuterSettable() {
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (Exception e) {
                throw new GenericException(e);
            }

            if (!isOuterSettable(field) && value != null) {
                throw new GenericException(GenericMessageMeta.NOT_OUTER_SETTABLE_FIELD, field, Response.Status.BAD_REQUEST);
            }
        }
    }

	/**
	 * @param clazz
	 * @param map
	 * @param <T>
	 * @return
	 */
	public static <T extends Domain> T fromHashMap(Class<T> clazz, Map<String, Object> map) {
		T instance;
		try {
			instance = clazz.newInstance();
		} catch (Exception e) {
			throw new GenericException(e);
		}

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			for (final Field field : instance.getClass().getDeclaredFields()) {
				if (field.getName().equals(entry.getKey())) {
					try {
						Method method = clazz.getDeclaredMethod(
								getSetterName(SetterPrefix.BEAN_SETTER, field),
								field.getType());
						method.invoke(instance, entry.getValue());
					} catch (Exception e) {
						throw new GenericException(e);
					}
				}
			}
		}

		return instance;
	}

    /**
     * @param unless
     * @return
     */
    public HashMap<String, Object> convertToHashMap(Field[] unless) {
        Field[] fields = getClass().getDeclaredFields();
        List<Field> result;

        if (unless != null) {
        	List<Field> unlessList = Arrays.asList(unless);
			result = Arrays.stream(fields)
					.filter(field -> !unlessList.contains(field))
					.collect(Collectors.toList());
        } else {
            result = Arrays.asList(fields);
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        for (Field field : result) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                params.put(field.getName(), value);
            } catch (Exception e) {
                throw new GenericException(e);
            }
        }

        return params;
    }

	public static String getTableName(Class<? extends Domain> clazz) {
		return clazz.getSimpleName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
	}

	public static Field getPrimaryKeyField(Class<? extends Domain> clazz) {
		Field[] fields = Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(PrimaryKey.class))
				.toArray(Field[]::new);

		if (fields == null || fields.length < 1) {
			throw new GenericException(GenericMessageMeta.NOT_EXIST_PRIMARYKEY, clazz, Response.Status.INTERNAL_SERVER_ERROR);
		}

		return fields[0];
	}

	public static boolean containsField(Class<? extends Domain> clazz, String fieldName) {
		Field[] fields = Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.getName().equals(fieldName))
				.toArray(Field[]::new);

		return fields != null && fieldName.length() >= 1;
	}
}
