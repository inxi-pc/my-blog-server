package myblog;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import myblog.provider.CORSFilter;
import myblog.provider.ErrorMapper;
import myblog.provider.ExceptionMapper;
import org.apache.ibatis.io.Resources;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.ibatis.io.Resources.getResourceAsStream;

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
    private static final String CONFIG_NAME = "myblog/config.properties";

    /**
     * App config
     *
     */
    private static Properties config;

    /**
     * Register component
     *
     */
    private App() {
        packages("myblog.resource");
        register(JacksonJsonProvider.class);
        register(ExceptionMapper.class);
        register(ErrorMapper.class);
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
        try {
            InputStream in = App.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
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
