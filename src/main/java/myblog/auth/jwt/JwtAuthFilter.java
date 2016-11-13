package myblog.auth.jwt;

import myblog.auth.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter<P extends Principal> extends AuthFilter<String, P> {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!requestContext.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS)) {
            Optional<String> jwtCredential = getJwtCredentials(requestContext.getHeaders().getFirst("Authorization"));

            if (!jwtCredential.isPresent()
                    || !authenticate(requestContext, jwtCredential.get(), "Bear")) {
                throw new WebApplicationException(this.unauthorizedHandler.buildResponse(this.prefix, this.realm));
            }
        }
    }

    private Optional<String> getJwtCredentials(String header) {
        if (header == null) {
            return Optional.empty();
        }

        final int space = header.indexOf(' ');
        if (space <= 0) {
            return Optional.empty();
        }

        final String method = header.substring(0, space);
        if (!this.prefix.equalsIgnoreCase(method)) {
            return Optional.empty();
        }

        return Optional.of(header.substring(space + 1));
    }

    public static class Builder<P extends Principal> extends
            AuthFilterBuilder<String, P, JwtAuthFilter<P>> {

        @Override
        protected JwtAuthFilter<P> newInstance() {
            return new JwtAuthFilter<P>();
        }
    }
}
