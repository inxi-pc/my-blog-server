package myblog.auth;

import myblog.exception.AuthenticationException;

import java.security.Principal;
import java.util.Optional;

public interface Authenticator<C, P extends Principal> {

    Optional<P> authenticate(C credentials) throws AuthenticationException;
}
