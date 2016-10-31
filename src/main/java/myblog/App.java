package myblog;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import myblog.auth.AuthDynamicFeature;
import myblog.auth.jwt.JwtAuthFilter;
import myblog.domain.User;
import myblog.provider.CORSFilter;
import myblog.provider.JwtAuthenticator;
import myblog.provider.MyErrorMapper;
import myblog.provider.MyExceptionMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

/**
 * My blog main class
 *
 */
public class App extends ResourceConfig {

    /**
     * Server root URI
     *
     */
    private static URI BASE_URI = URI.create("http://localhost:8888/");

    /**
     * App config file name
     *
     */
    private static final String SERVER_CONFIG_FILENAME = "config.properties";

    /**
     * App config
     *
     */
    private static Properties config;


    private static Logger logger = LogManager.getLogger(App.class);

    /**
     * Register component
     *
     */
    private App() {
        packages("myblog.resource");

        register(JacksonJsonProvider.class);
        register(MyExceptionMapper.class);
        register(MyErrorMapper.class);
        register(CORSFilter.class);
        register(new AuthDynamicFeature(new JwtAuthFilter.Builder<User>()
                .setAuthenticator(new JwtAuthenticator())
                .setPrefix("Bearer")
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "Hello World!Blog server");

        loadApplicationConfig();

        try {
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new App(), false);
            server.start();
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
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
            InputStream in = App.class.getClassLoader().getResourceAsStream(SERVER_CONFIG_FILENAME);
            config.load(in);
            in.close();
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }

        logger.log(Level.INFO, config);
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
                throw new RuntimeException("No configuration value found: jwtKey");
            }
        } else {
            throw new RuntimeException("No configuration found");
        }
    }

    public static String getJwtKey() {
        if (config != null) {
            String jwtKey;
            if ((jwtKey = config.getProperty("jwtKey")) != null) {
                return jwtKey;
            } else {
                throw new RuntimeException("No configuration value found: jwtKey");
            }
        } else {
            throw new RuntimeException("No configuration found");
        }
    }
}

