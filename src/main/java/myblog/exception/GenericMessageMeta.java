package myblog.exception;

public enum GenericMessageMeta implements MessageMeta {

    NOT_INSERTABLE_FIELD("Filed %s: Not insertabled"),

    NOT_NULLABLE_FIELD("Field %s: Not nullable"),

    NOT_OUTER_SETTABLE_FIELD("Field %s: Not outer settable"),

    NOT_UPDATABLE_FIELD("Field %s: Not updatable"),

    NOT_VALID_VALUE_FIELD("Field %s: Not valid value"),

    NULL_OBJECT_TO_INSERT("Object %s: Null to do insert"),

    NULL_ID_TO_DELETE("Id %s: Null to do delete"),

    NULL_ID_TO_UPDATE("Id %s: Null to do update"),

    NULL_ID_TO_QUERY("Id %s: Null to do query"),

    INVALID_ID_TO_DELETE("Id %s: Invalid value to do delete"),

    INVALID_ID_TO_UPDATE("Id %s: Invalid value to do update"),

    INVALID_ID_TO_QUERY("Id %s: Invalid value to do query"),

    INVALID_PARAM_TO_QUERY("Param %s: Invalid value to do query"),

    NOT_FOUND_OBJECT("Object %s: Not found"),

    NOT_FOUND_OBJECT_TO_DELETE("Object %s: Not found to do delete"),

    NOT_FOUND_OBJECT_TO_UPDATE("Object %s: Not found to do update"),

    EXIST_OBJECT("Object %s: Exist already");

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
