package myblog.exception;

public enum GenericMessageMeta implements MessageMeta {

    NOT_INSERTABLE_FIELD("Filed %s: Not insertabled"),

    NOT_NULLABLE_FIELD("Field %s: Not nullable"),

    NOT_OUTER_SETTABLE_FIELD("Field %s: Not outer settable"),

    NOT_UPDATABLE_FIELD("Field %s: Not updatable"),

    INVALID_VALUE_FIELD("Field %s: Not valid value"),

    NULL_OBJECT("Object %s: Null"),

    NULL_ID("Id %s: Null"),

    INVALID_ID("Id %s: Invalid value"),

    INVALID_PARAM("Param %s: Invalid value"),

    NOT_FOUND_OBJECT("Object %s: Not found"),

    EXISTED_OBJECT("Object %s: Exist already");

    private String format;

    GenericMessageMeta(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }

    public boolean hasPlaceholder() {
        return true;
    }
}
