package myblog.domain;

import java.lang.reflect.Field;
import java.util.List;

public interface Credential {

    List<Field> getIdentifierFields();

    boolean hasIdentifier();

    Object getIdentifier();

    boolean hasPassword();

    Object getPassword();

    void encryptPassword();
}
