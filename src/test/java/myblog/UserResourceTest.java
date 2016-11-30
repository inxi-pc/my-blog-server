package myblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import myblog.domain.User;
import myblog.resource.UserResource;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.external.ExternalTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class);
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new ExternalTestContainerFactory();
    }

    @Override
    protected Client getClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        return ClientBuilder.newClient(new ClientConfig()
                .register(new JacksonJsonProvider(objectMapper)));
    }

//    @Test
//    public void registerTest() {
//        User user = new User();
//        user.setUser_name("test");
//        user.setUser_password("testtestt");
//        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);
//        Response response = target("/users/register").request().post(userEntity);
//        Assert.assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());
//    }
//
//    @Test
//    public void loginTest() {
//        User user = new User();
//        user.setUser_name("test");
//        user.setUser_password("testtestt");
//        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);
//        Response response = target("/users/login").request().post(userEntity);
//        Assert.assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());
//
//        String tokenAndUser = response.readEntity(String.class);
//        if (tokenAndUser.length() <= 0) {
//            Assert.fail("Invalid response entity");
//        }
//    }
}
