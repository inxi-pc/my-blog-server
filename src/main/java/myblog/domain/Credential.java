package myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import myblog.exception.DomainException;

import java.lang.reflect.Field;
import java.util.List;

public interface Credential {

    @JsonIgnore
    List<Field> getIdentifierFields();

    @JsonIgnore
    List<Object> getIdentifierValues() throws DomainException;

    boolean hasIdentifier();

    @JsonIgnore
    Object getIdentifier() throws DomainException;

    boolean hasPassword();

    @JsonIgnore
    String getPassword() throws DomainException;

    void encryptPassword() throws DomainException;

    boolean validPassword(String compared) throws DomainException;
}
