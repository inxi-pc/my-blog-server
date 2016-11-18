package myblog.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import myblog.App;
import myblog.auth.Authenticator;
import myblog.domain.Domain;
import myblog.domain.User;

import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
public class JwtAuthenticator implements Authenticator<String, User> {

    @Override
    public Optional<User> authenticate(String jwtCredentials) {
        Jwt jwt = Jwts.parser().setSigningKey(App.getJwtKey()).parse(jwtCredentials);
        Claims body = (Claims) jwt.getBody();
        User user = Domain.fromHashMap(User.class, body);

        return Optional.of(user);
    }
}
