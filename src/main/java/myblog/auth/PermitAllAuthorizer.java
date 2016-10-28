package myblog.auth;

import java.security.Principal;

public class PermitAllAuthorizer<P extends Principal> implements Authorizer<P> {

    @Override
    public boolean authorize(P principal, String role) {
        return true;
    }
}
