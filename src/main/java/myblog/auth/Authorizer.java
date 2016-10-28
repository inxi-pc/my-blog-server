package myblog.auth;

import java.security.Principal;

public interface Authorizer<P extends Principal> {

    boolean authorize(P principal, String role);
}
