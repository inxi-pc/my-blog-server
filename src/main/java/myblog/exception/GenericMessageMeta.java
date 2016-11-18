package myblog.exception;

public enum GenericMessageMeta implements MessageMeta {

    FIELD_NOT_INSERTABLE("Unexpected %s: Not insertabled"),

    FIELD_NOT_NULLABLE("Unexpected %s: Not nullable"),

    FIELD_NOT_OUTER_SETTABLE("Unexpected %s: Not outer settable"),

    FIELD_NOT_UPDATABLE("Unexpected %s: Not updatable"),

    FIELD_NOT_VALID_VALUE("Unexpected %s: Not valid value"),

    NULL_INSERTED_OBJECT("Null %s for inserting"),

    NULL_DELETED_ID("Null %s id for deleting"),

    NULL_UPDATED_ID("Null %s id for updating"),

    NULL_QUERY_ID("Null %s id for querying"),

    INVALID_DELETED_ID("Invalid %s id for deleting"),

    INVALID_UPDATED_ID("Invalid %s id for updating"),

    INVALID_QUERY_ID("Invalid %s id for querying"),

    INVALID_QUERY_PARAM("Invalid param %s for querying"),

    NOT_FOUND_OBJECT("Not found %s"),

    NOT_FOUND_DELETED_OBJECT("Not found deleted %s"),

    NOT_FOUND_UPDATED_OBJECT("Not found updated %s"),

    EXIST_OBJECT("Exist %s already");

    private String format;

    GenericMessageMeta(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }
}
