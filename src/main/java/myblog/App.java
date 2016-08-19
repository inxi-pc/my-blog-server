package myblog;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisDaoFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {

    private static URI BASE_URI = URI.create("http://localhost:8080/");

    public static void main(String[] args) {
        System.out.println("Hello World!Blog server");

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(1);
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new MyBlogApplication(), false);
            server.start();
        } catch (Exception ie) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ie);
        }
    }
}

class MyBlogApplication extends ResourceConfig {
    public MyBlogApplication() {
        packages("myblog.resource");
        register(JacksonJsonProvider.class);
    }
}
