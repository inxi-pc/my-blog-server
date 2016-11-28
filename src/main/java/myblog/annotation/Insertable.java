package myblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Insertable {

    /**
     * Indicate field can be nullable, when insert to db
     *
     * @return
     */
    boolean nullable();

    /**
     * Indicate field can be a default value, when insert to db
     *
     * @return
     */
    boolean defaultable() default false;
}
