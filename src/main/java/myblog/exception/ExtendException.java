package myblog.exception;

import java.util.HashMap;

public class ExtendException extends RuntimeException {

    /**
     * Exception status code will mapped to response status
     *
     */
    public int status;

    /**
     * Equal status
     *
     */
    public int code;

    /**
     * Exception doc link
     *
     */
    public String link;

    /**
     * All exception doc links
     *
     */
    private static final HashMap<Integer, String> LINKS;

    static {
        LINKS = new HashMap<Integer, String>();
        LINKS.put(200, "http://");
        LINKS.put(201, "http://");
        LINKS.put(204, "http://");
        LINKS.put(400, "http://");
        LINKS.put(404, "http://");
        LINKS.put(405, "http://");
        LINKS.put(409, "http://");
        LINKS.put(500, "http://");
    }

    public ExtendException(int status, String message) {
        super(message);

        this.status = status;
        this.code = status;
        this.link = LINKS.get(status);
    }

    public ExtendException(int status, Exception e) {
        super(e);

        this.status = status;
        this.code = status;
        this.link = LINKS.get(status);
    }

    public ResponseEntity toResponseEntity() {
        ResponseEntity entity = new ResponseEntity();
        entity.code = this.code;
        entity.status = this.status;
        entity.message = this.getMessage();
        entity.link = this.link;

        return entity;
    }
}

class ResponseEntity {
    /**
     * Exception status code will mapped to response status
     *
     */
    public int status;

    /**
     * Equal status
     *
     */
    public int code;

    /**
     * Exception doc link
     *
     */
    public String link;

    /**
     * Exception message
     *
     */
    public String message;
}

