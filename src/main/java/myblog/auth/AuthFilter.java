package myblog.auth;

import myblog.exception.AuthenticationException;
import org.glassfish.jersey.server.internal.LocalizationMessages;

import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public abstract class AuthFilter<C, P extends Principal> implements ContainerRequestFilter {

    protected String prefix;

    protected String realm;

    protected Authenticator<C, P> authenticator;

    protected Authorizer<P> authorizer;

    protected UnauthorizedHandler unauthorizedHandler;

    public abstract static class AuthFilterBuilder<C, P extends Principal, T extends AuthFilter<C, P>> {

        private String realm = "realm";

        private String prefix = "Basic";

        private Authenticator<C, P> authenticator;

        private Authorizer<P> authorizer = new PermitAllAuthorizer<P>();

        private UnauthorizedHandler unauthorizedHandler = new DefaultUnauthorizedHandler();

        /**
         * Sets the given realm
         *
         * @param realm a realm
         * @return the current builder
         */
        public AuthFilterBuilder<C, P, T> setRealm(String realm) {
            this.realm = realm;

            return this;
        }

        /**
         * Sets the given prefix
         *
         * @param prefix a prefix
         * @return the current builder
         */
        public AuthFilterBuilder<C, P, T> setPrefix(String prefix) {
            this.prefix = prefix;

            return this;
        }

        /**
         * Sets the given authorizer
         *
         * @param authorizer an {@link Authorizer}
         * @return the current builder
         */
        public AuthFilterBuilder<C, P, T> setAuthorizer(Authorizer<P> authorizer) {
            this.authorizer = authorizer;

            return this;
        }

        /**
         * Sets the given authenticator
         *
         * @param authenticator an {@link Authenticator}
         * @return the current builder
         */
        public AuthFilterBuilder<C, P, T> setAuthenticator(Authenticator<C, P> authenticator) {
            this.authenticator = authenticator;

            return this;
        }

        /**
         * Sets the given unauthorized handler
         *
         * @param unauthorizedHandler an {@link UnauthorizedHandler}
         * @return the current builder
         */
        public AuthFilterBuilder<C, P, T> setUnauthorizedHandler(UnauthorizedHandler unauthorizedHandler) {
            this.unauthorizedHandler = unauthorizedHandler;

            return this;
        }

        /**
         * Builds an instance of the filter with a provided authenticator,
         * an authorizer, a prefix, and a realm.
         *
         * @return a new instance of the filter
         */
        public T buildAuthFilter() {
            if (realm == null) {
                throw new IllegalArgumentException("Realm is not set");
            }
            if (prefix == null) {
                throw new IllegalArgumentException("Prefix is not set");
            }
            if (authenticator == null) {
                throw new IllegalArgumentException("Authenticator is not set");
            }
            if (authorizer == null) {
                throw new IllegalArgumentException("Authorizer is not set");
            }
            if (unauthorizedHandler == null) {
                throw new IllegalArgumentException("Unauthorized handler is not set");
            }

            final T authFilter = newInstance();
            authFilter.authorizer = authorizer;
            authFilter.authenticator = authenticator;
            authFilter.prefix = prefix;
            authFilter.realm = realm;
            authFilter.unauthorizedHandler = unauthorizedHandler;

            return authFilter;
        }

        protected abstract T newInstance();
    }

    protected boolean authenticate(ContainerRequestContext requestContext, C credentials, String scheme) {
        if (credentials == null) {
            return false;
        }

        final Optional<P> principal;
        try {
            principal = this.authenticator.authenticate(credentials);
        } catch (AuthenticationException e) {
            throw new ForbiddenException(LocalizationMessages.USER_NOT_AUTHORIZED());
        }

        if (!principal.isPresent()) {
            return false;
        }

        final SecurityContext securityContext = requestContext.getSecurityContext();
        final boolean secure = securityContext != null && securityContext.isSecure();

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return principal.get();
            }

            @Override
            public boolean isUserInRole(String role) {
                return AuthFilter.this.authorizer.authorize(principal.get(), role);
            }

            @Override
            public boolean isSecure() {
                return secure;
            }

            @Override
            public String getAuthenticationScheme() {
                return scheme;
            }
        });

        return true;
    }
}
