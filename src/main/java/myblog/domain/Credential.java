package myblog.domain;

import myblog.exception.DomainException;

import java.lang.reflect.Field;
import java.util.List;

public interface Credential {

    List<Field> getIdentifierFields();

    boolean hasIdentifier();

    Object getIdentifier() throws DomainException;

    boolean hasPassword();

    String getPassword() throws DomainException;

    void encryptPassword() throws DomainException;

    boolean validPassword(String compared) throws DomainException;
}
