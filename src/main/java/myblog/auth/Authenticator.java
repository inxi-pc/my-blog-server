package myblog.auth;

import java.security.Principal;
import java.util.Optional;

public interface Authenticator<C, P extends Principal> {

    Optional<P> authenticate(C credentials);
}
