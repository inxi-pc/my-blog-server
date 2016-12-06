package myblog.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

abstract class ThrowableMapper<T extends Throwable> implements ExceptionMapper<T> {

    /**
     * Logger for all Throwable and its sub
     */
    protected static Logger logger = LoggerFactory.getLogger(ThrowableMapper.class);

    /**
     * @param e #{@link Exception} and #{@link Error}
     * @return Response
     */
    abstract public Response toResponse(T e);
}
