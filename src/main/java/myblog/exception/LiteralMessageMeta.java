package myblog.exception;

public enum LiteralMessageMeta implements MessageMeta {

    ILLEGAL_QUANTITY_IDENTIFIER("Illegal quantity of identifier"),

    ILLEGAL_QUANTITY_PASSWORD("Illegal quantity of password"),

    NULL_CREDENTIAL_TO_QUERY("Null credential to query"),

    INCORRECT_PASSWORD("Incorrect password"),

    NULL_USER_TO_REGISTER("Null user to register"),

    NULL_USER_TO_LOGIN("Null user to login"),

    NULL_PARAM_TO_QUERY("Null param to query"),

    EMPTY_PARAM_TO_QUERY("Empty param to query"),

    NOT_FOUND_PARENT_CATEGORY("Not found parent category"),

    LACK_PARENT_CATEGORY_TO_GEN_TREE("Lack parent category to gen tree");

    private String format;

    LiteralMessageMeta(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }

    public boolean hasPlaceholder() {
        return false;
    }
}
