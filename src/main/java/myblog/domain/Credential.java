package myblog.domain;

public interface Credential {

    boolean hasIdentifier();

    Object getIdentifier();

    boolean hasPassword();

    Object getPassword();

    void encryptPassword();
}
