package myblog.exception;

import javax.ws.rs.core.Response;

public interface MessageMeta {

    String getFormat();

    Response.Status getStatus();
}