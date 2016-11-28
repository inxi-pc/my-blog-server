package myblog.exception;

public enum LiteralMessageMeta implements MessageMeta {

    ILLEGAL_QUANTITY_IDENTIFIER("Illegal quantity of identifier"),

    ILLEGAL_QUANTITY_PASSWORD("Illegal quantity of password"),

    INCORRECT_PASSWORD("Incorrect password"),

    NULL_QUERY_PARAM_LIST("Null query param"),

    EMPTY_QUERY_PARAM_LIST("Empty query param list"),

    NOT_FOUND_PARENT_CATEGORY("Not found parent category");

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
