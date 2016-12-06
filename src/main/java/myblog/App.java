package myblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import myblog.auth.AuthDynamicFeature;
import myblog.auth.jwt.JwtAuthFilter;
import myblog.domain.User;
import myblog.provider.CORSFilter;
import myblog.provider.JwtAuthenticator;
import myblog.provider.MyErrorMapper;
import myblog.provider.MyExceptionMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.Properties;

/**
 * My blog main class
 */
public class App extends ResourceConfig {

    /**
     * Server root URI
     */
    private static final URI BASE_URI = URI.create("http://localhost:8888/");

    /**
     * App logger
     */
    public static Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * App config
     */
    private static Properties config;

    /**
     * Register component
     */
    public App() {
        packages("myblog.resource");

        register(MyExceptionMapper.class);
        register(MyErrorMapper.class);
        register(CORSFilter.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        register(new JacksonJsonProvider(objectMapper));

        register(new AuthDynamicFeature(new JwtAuthFilter.Builder<User>()
                .setAuthenticator(new JwtAuthenticator())
                .setPrefix("Bearer")
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
    }

    public static void main(String[] args) {
        logger.info("Hello World!Blog server");

        loadApplicationConfig();

        try {
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new App(), false);
            server.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Get application config properties
     *
     * @return
     */
    private static void loadApplicationConfig() {
        config = new Properties();
        try {
            InputStream in = App.class.getClassLoader().getResourceAsStream("config.properties");
            config.load(in);
            in.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Check if running on debug model
     *
     * @return
     */
    public static boolean isDebug() {
        if (config != null) {
            String debug;
            if ((debug = config.getProperty("debug")) != null) {
                return Boolean.parseBoolean(debug);
            } else {
                throw new RuntimeException("Not found configuration key: `jwtKey`");
            }
        } else {
            throw new RuntimeException("Not found configuration");
        }
    }

    /**
     * Get encrypt jwt secret key
     *
     * @return
     */
    public static byte[] getJwtKey() {
        if (config != null) {
            String jwtKey;
            if ((jwtKey = config.getProperty("jwtKey")) != null) {
                String base64Key = DatatypeConverter.printBase64Binary(jwtKey.getBytes());

                return DatatypeConverter.parseBase64Binary(base64Key);
            } else {
                throw new RuntimeException("Not found configuration key: `jwtKey`");
            }
        } else {
            throw new RuntimeException("Not found configuration");
        }
    }

    /**
     * Get jwt expired time
     *
     * @return
     */
    public static Date getJwtExpiredTime() {
        if (config != null) {
            String jwtKey;
            if ((jwtKey = config.getProperty("jwtExpiredTime")) != null) {
                Long expiredAtLong = System.currentTimeMillis() + Long.parseLong(jwtKey);

                return new Date(expiredAtLong);
            } else {
                throw new RuntimeException("Not found configuration key: `jwtExpiredTime`");
            }
        } else {
            throw new RuntimeException("Not found configuration");
        }
    }
}

