package myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import myblog.Helper;
import myblog.annotation.*;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.exception.LiteralMessageMeta;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class User extends Domain implements Principal, Credential {

    @PrimaryKey
    private Integer user_id;

    @OuterSettable
    @Insertable(nullable = false)
    @Identifier
    private String user_name;

    @OuterSettable
    @Insertable(nullable = true)
    @Identifier
    private Integer user_telephone;

    @OuterSettable
    @Insertable(nullable = true)
    @Identifier
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
    @JsonIgnore
    public String getName() {
        return this.user_name;
    }

    @Override
    public List<Field> getIdentifierFields() {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Identifier.class)) {
                fields.add(field);
            }
        }

        return fields;
    }

    @Override
    public List<Object> getIdentifierValues() {
        List<Field> fields = getIdentifierFields();
        List<Object> values = new ArrayList<Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value != null) {
                    values.add(value);
                }
            } catch (Exception e) {
                throw new GenericException(e);
            }
        }

        return values;
    }

    @Override
    public boolean hasIdentifier() {
        return getIdentifierValues().size() == 1;
    }

    @Override
    public Object getIdentifier() {
        if (hasIdentifier()) {
            return getIdentifierValues().get(0);
        } else {
            throw new GenericException(LiteralMessageMeta.ILLEGAL_QUANTITY_IDENTIFIER, Response.Status.BAD_REQUEST);
        }
    }

    @Override
    public boolean hasPassword() {
        return this.user_password != null;
    }

    @Override
    public String getPassword() {
        if (hasPassword()) {
            return this.user_password;
        } else {
            throw new GenericException(LiteralMessageMeta.ILLEGAL_QUANTITY_PASSWORD, Response.Status.BAD_REQUEST);
        }
    }

    @Override
    public void encryptPassword() {
        try {
            int iterations = 1000;
            byte[] salt = genSalt();
            PBEKeySpec spec = new PBEKeySpec(getPassword().toCharArray(), salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            this.user_password = iterations + ":" + convertToHex(salt) + ":" + convertToHex(hash);
        } catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @Override
    public boolean validatePassword(String compared) {
        try {
            String[] parts = getPassword().split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(compared.toCharArray(), salt, iterations, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for(int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }

            return diff == 0;
        } catch (Exception e) {
            throw new GenericException(e);
        }
    }

    private static byte[] genSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        return salt;
    }

    private static String convertToHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

    public Integer getUser_id() {
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

    public Boolean getUser_enabled() {
        return user_enabled;
    }

    public void setUser_id(Integer userId) {
        if (isValidUserId(userId)) {
            this.user_id = userId;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "user_id", Response.Status.BAD_REQUEST);
        }
    }

    public void setUser_name(String userName) {
        if (isValidUserName(userName)) {
            this.user_name = userName;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "username", Response.Status.BAD_REQUEST);
        }
    }

    public void setUser_telephone(Integer userTelephone) {
        this.user_telephone = userTelephone;
    }

    public void setUser_email(String userEmail) {
        if (isValidUserEmail(userEmail)) {
            this.user_email = userEmail;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "user_email", Response.Status.BAD_REQUEST);
        }
    }

    public void setUser_password(String userPassword) {
        if (isValidUserPassword(userPassword)) {
            this.user_password = userPassword;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "user_password", Response.Status.BAD_REQUEST);
        }
    }

    public void setUser_created_at(String userCreatedAt) {
        if (isValidUserCreatedAt(userCreatedAt)) {
            this.user_created_at = userCreatedAt;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "user_created_at", Response.Status.BAD_REQUEST);
        }
    }

    public void setUser_updated_at(String userUpdatedAt) {
        if (isValidUserUpdatedAt(userUpdatedAt)) {
            this.user_updated_at = userUpdatedAt;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "user_updated_at", Response.Status.BAD_REQUEST);
        }
    }

    public void setUser_enabled(Boolean userEnabled) {
        this.user_enabled = userEnabled;
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

    public static boolean isValidUserId(Integer userId) {
        return userId == null || userId > 0;
    }

    public static boolean isValidUserName(String userName) {
        if (userName == null) {
            return true;
        } else {
            Pattern p = Pattern.compile("[a-zA-Z_]+");

            return !userName.isEmpty() && userName.length() < 30 && p.matcher(userName).matches();
        }
    }

    public static boolean isValidUserEmail(String userEmail) {
        if (userEmail == null) {
            return true;
        } else {
            Pattern p = Pattern.compile("[a-zA-Z1-9_-]+@[a-zA-Z1-9_-]+\\.\\w+");

            return !userEmail.isEmpty() && userEmail.length() < 30 && p.matcher(userEmail).matches();
        }
    }

    public static boolean isValidUserPassword(String userPassword) {
        return userPassword == null || (userPassword.length() > 8 && userPassword.length() < 200);
    }

    public static boolean isValidUserCreatedAt(String userCreatedAt) {
        return userCreatedAt == null || Helper.isValidDataTimeFormat(userCreatedAt);
    }

    public static boolean isValidUserUpdatedAt(String userUpdatedAt) {
        return userUpdatedAt == null || Helper.isValidDataTimeFormat(userUpdatedAt);
    }
}
