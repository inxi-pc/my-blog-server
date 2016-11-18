package myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;
import java.util.List;

public interface Credential {

    @JsonIgnore
    List<Field> getIdentifierFields();

    @JsonIgnore
    List<Object> getIdentifierValues();

    boolean hasIdentifier();

    @JsonIgnore
    Object getIdentifier();

    boolean hasPassword();

    @JsonIgnore
    String getPassword();

    void encryptPassword();

    boolean validPassword(String compared);
}
