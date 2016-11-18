package myblog.exception;

public enum LiteralMessageMeta implements MessageMeta {

    ILLEGAL_NUMBER_OF_IDENTIFIER("Illegal number of identifier"),

    ILLEGAL_NUMBER_OF_PASSWORD("Illegal number of password"),

    NULL_REGISTERED_USER("Null user for register"),

    NULL_LOGIN_USER("Null user for login"),

    NULL_QUERY_PARAM("Query param is null"),

    NOT_FOUND_CATEGORY_PARENT("Not found category parent"),

    CATEGORY_LACK_PARENT("Category lack parent"),

    EMPTY_QUERY_PARAM("Query param is empty"),

    INCORRECT_PASSWORD("Incorrect password");

    private String format;

    LiteralMessageMeta(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }
}
