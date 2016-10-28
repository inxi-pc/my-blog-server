package myblog.domain;

import myblog.annotation.PrimaryKey;

import javax.security.auth.Subject;
import java.security.Principal;

public class User implements Principal {

    @PrimaryKey
    public int user_id;
    public String user_username;
    public String user_telephone;
    public String user_email;
    public String user_password;
    public String user_created_at;
    public String user_updated_at;
    public boolean user_enabled;

    @Override
    public String getName() {
        return this.user_username;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
