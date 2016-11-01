package myblog.domain;

import myblog.Helper;
import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.PrimaryKey;
import myblog.annotation.Updatable;

import java.security.Principal;
import java.util.Date;
import java.util.regex.Pattern;

public class User extends Domain implements Principal, Credential {

    @PrimaryKey
    private Integer user_id;

    @OuterSettable
    @Insertable(nullable = false)
    private String user_name;

    @OuterSettable
    @Insertable(nullable = true)
    private Integer user_telephone;

    @OuterSettable
    @Insertable(nullable = true)
    private String user_email;

    @OuterSettable
    @Insertable(nullable = false)
    private String user_password;

    @Insertable(nullable = false, defaultable = true)
    @Updatable
    private String user_created_at;

    @Insertable(nullable = false, defaultable = true)
    @Updatable
    private String user_updated_at;

    @Insertable(nullable = false, defaultable = true)
    @Updatable
    private Boolean user_enabled;

    @Override
    public String getName() {
        return this.user_name;
    }

    @Override
    public boolean hasIdentifier() {
        return this.user_name != null
                || this.user_telephone != null
                || this.user_email != null;
    }

    @Override
    public Object getIdentifier() {
        if (this.user_name != null) {
            return this.user_name;
        }

        if (this.user_email != null) {
            return this.user_email;
        }

        if (this.user_telephone != null) {
            return this.user_telephone;
        }

        return null;
    }

    @Override
    public boolean hasPassword() {
        return this.user_password != null;
    }

    @Override
    public Object getPassword() {
        return this.user_password;
    }

    @Override
    public void encryptPassword() {

    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public Integer getUser_telephone() {
        return user_telephone;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_created_at() {
        return user_created_at;
    }

    public String getUser_updated_at() {
        return user_updated_at;
    }

    public boolean getUser_enabled() {
        return user_enabled;
    }

    public static boolean isValidUserId(Integer userId) {
        if (userId == null) {
            return true;
        } else {
            return userId > 0;
        }
    }

    public static boolean isValidUserName(String userName) {
        if (userName == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidUserTelephone(Integer userTelephone) {
        if (userTelephone == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidUserEmail(String userEmail) {
        if (userEmail == null) {
            return true;
        } else {
            Pattern p = Pattern.compile("[a-zA-Z1-9_-]+@[a-zA-Z1-9_-]+\\.\\w+");

            return p.matcher(userEmail).matches();
        }
    }

    public static boolean isValidUserPassword(String userPassword) {
        if (userPassword == null) {
            return true;
        } else {
            return userPassword.length() > 8;
        }
    }

    public static boolean isValidUserCreatedAt(String userCreatedAt) {
        if (userCreatedAt == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidUserUpdatedAt(String userUpdatedAt) {
        if (userUpdatedAt == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidUserEnabled(Boolean userEnabled) {
        if (userEnabled == null) {
            return true;
        } else {
            return true;
        }
    }

    public void setUser_id(Integer userId) {
        if (isValidUserId(userId)) {
            this.user_id = userId;
        } else {
            throw new IllegalArgumentException("Unexpected user id: Invalid value");
        }
    }

    public void setUser_username(String userName) {
        if (isValidUserName(userName)) {
            this.user_name = userName;
        } else {
            throw new IllegalArgumentException("Unexpected user name: Invalid value");
        }
    }

    public void setUser_telephone(Integer userTelephone) {
        if (isValidUserTelephone(userTelephone)) {
            this.user_telephone = userTelephone;
        } else {
            throw new IllegalArgumentException("Unexpected user telephone: Invalid value");
        }
    }

    public void setUser_email(String userEmail) {
        if (isValidUserEmail(userEmail)) {
            this.user_email = userEmail;
        } else {
            throw new IllegalArgumentException("Unexpected user email: Invalid value");
        }
    }

    public void setUser_password(String userPassword) {
        if (isValidUserPassword(userPassword)) {
            this.user_password = userPassword;
        } else {
            throw new IllegalArgumentException("Unexpected user password: Invalid value");
        }
    }

    public void setUser_created_at(String userCreatedAt) {
        if (isValidUserCreatedAt(userCreatedAt)) {
            this.user_created_at = userCreatedAt;
        } else {
            throw new IllegalArgumentException("Unexpected user created at: Invalid value");
        }
    }

    public void setUser_updated_at(String userUpdatedAt) {
        if (isValidUserUpdatedAt(userUpdatedAt)) {
            this.user_updated_at = userUpdatedAt;
        } else {
            throw new IllegalArgumentException("Unexpected user updated at: Invalid value");
        }
    }

    public void setUser_enabled(Boolean userEnabled) {
        if (isValidUserEnabled(userEnabled)) {
            this.user_enabled = userEnabled;
        } else {
            throw new IllegalArgumentException("Unexpected user enabled: Invalid value");
        }
    }

    public void setDefaultUser_created_at() {
        this.user_created_at = Helper.formatDatetimeUTC(new Date());
    }

    public void setDefaultUser_updated_at() {
        this.user_updated_at = Helper.formatDatetimeUTC(new Date());
    }

    public void setDefaultUser_enabled() {
        this.user_enabled = true;
    }
}
