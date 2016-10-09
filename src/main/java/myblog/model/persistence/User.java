package myblog.model.persistence;

import myblog.model.annotation.PrimaryKey;

public class User {

    @PrimaryKey
    public int user_id;
    public String user_username;
    public String user_telephone;
    public String user_email;
    public String user_password;
    public String user_created_at;
    public String user_updated_at;
    public boolean user_enabled;
}
