package myblog.provider;

import myblog.auth.Authenticator;
import myblog.auth.basic.BasicCredentials;
import myblog.domain.User;
import myblog.exception.AuthenticationException;

import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        return Optional.of(new User());
    }
}
