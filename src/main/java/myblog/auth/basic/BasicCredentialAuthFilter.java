package myblog.auth.basic;

import myblog.auth.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public class BasicCredentialAuthFilter<P extends Principal> extends AuthFilter<BasicCredentials, P> {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final Optional<BasicCredentials> basicCredentials =
                getBasicCredentials(requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        if (!basicCredentials.isPresent()
                || !authenticate(requestContext, basicCredentials.get(), SecurityContext.BASIC_AUTH)) {
            throw new WebApplicationException(this.unauthorizedHandler.buildResponse(this.prefix, this.realm));
        }
    }

    private Optional<BasicCredentials> getBasicCredentials(String header) {
        if (header == null) {
            return Optional.empty();
        }

        final int space = header.indexOf(' ');
        if (space <= 0) {
            return Optional.empty();
        }

        final String method = header.substring(0, space);
        if (!prefix.equalsIgnoreCase(method)) {
            return Optional.empty();
        }

        final String decoded;
        try {
            decoded = new String(Base64.getDecoder().decode(header.substring(space + 1)), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        // Decoded credentials is 'username:password'
        final int i = decoded.indexOf(':');
        if (i <= 0) {
            return Optional.empty();
        }

        final String username = decoded.substring(0, i);
        final String password = decoded.substring(i + 1);

        return Optional.of(new BasicCredentials(username, password));
    }

    public static class Builder<P extends Principal> extends
            AuthFilterBuilder<BasicCredentials, P, BasicCredentialAuthFilter<P>> {

        @Override
        protected BasicCredentialAuthFilter<P> newInstance() {
            return new BasicCredentialAuthFilter<P>();
        }
    }
}
