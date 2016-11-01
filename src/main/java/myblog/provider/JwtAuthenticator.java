package myblog.provider;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import myblog.App;
import myblog.auth.Authenticator;
import myblog.domain.User;
import myblog.exception.AuthenticationException;

import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Optional;

@Provider
public class JwtAuthenticator implements Authenticator<String, User> {

    @Override
    public Optional<User> authenticate(String jwtCredentials) throws AuthenticationException {
        Jwt jwt = Jwts.parser().setSigningKey(App.getJwtKey()).parse(jwtCredentials);
        if (jwt != null) {
            System.out.println(jwt);
        }
        return null;
    }
}
