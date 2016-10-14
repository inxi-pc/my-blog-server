package myblog;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import myblog.provider.CORSFilter;
import myblog.provider.MyErrorMapper;
import myblog.provider.MyExceptionMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Server config file name
     *
     */
    private static final String SERVER_CONFIG_FILENAME = "config.properties";
    private static final String LANG_FILENAME = "lang.properties";

    /**
     * App config
     *
     */
    private static Properties config;
    private static Properties lang;

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
    }

    public static void main(String[] args) {
        System.out.println("Hello World!Blog server");
        loadApplicationConfig();

        try {
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new App(), false);
            server.start();
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Get application config properties
     *
     * @return
     */
    private static void loadApplicationConfig() {
        config = new Properties();
        lang = new Properties();
        try {
            InputStream in = App.class.getClassLoader().getResourceAsStream(SERVER_CONFIG_FILENAME);
            config.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if running on debug model
     *
     * @return
     */
    public static boolean isDebug() {
        if (config != null) {
           return Boolean.parseBoolean(config.getProperty("debug"));
        } else {
            loadApplicationConfig();
            return Boolean.parseBoolean(config.getProperty("debug"));
        }
    }
}

