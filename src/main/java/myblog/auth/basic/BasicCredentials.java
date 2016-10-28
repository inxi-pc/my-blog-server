package myblog.auth.basic;

import java.util.Objects;

public class BasicCredentials {

    private String username;

    private String password;

    /**
     * Creates a new BasicCredentials with the given username and password.
     *
     * @param username the username
     * @param password the password
     */
    public BasicCredentials(String username, String password) {
        if (username != null) {
            this.username = username;
        } else {
            throw new NullPointerException("Unexpected username: Null pointer");
        }

        if (password != null) {
            this.password = password;
        } else {
            throw new NullPointerException("Unexpected password: Null pointer");
        }
    }

    /**
     * Returns the credentials' username.
     *
     * @return the credentials' username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the credentials' password.
     *
     * @return the credentials' password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BasicCredentials other = (BasicCredentials) obj;

        return Objects.equals(this.username, other.username) && Objects.equals(this.password, other.password);
    }
}
