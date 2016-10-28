package myblog.auth;

import org.glassfish.jersey.server.model.AnnotatedMethod;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;

public class AuthDynamicFeature implements DynamicFeature {

    private final ContainerRequestFilter authFilter;

    private final Class<? extends ContainerRequestFilter> authFilterClass;

    public AuthDynamicFeature(ContainerRequestFilter authFilter) {
        this.authFilterClass = null;
        this.authFilter = authFilter;
    }

    public AuthDynamicFeature(Class<? extends ContainerRequestFilter> authFilterClass) {
        this.authFilterClass = authFilterClass;
        this.authFilter = null;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        Method am = resourceInfo.getResourceMethod();
        Class as = resourceInfo.getResourceClass();

        boolean hasAnnotation = am.isAnnotationPresent(RolesAllowed.class)
                || am.isAnnotationPresent(PermitAll.class)
                || am.isAnnotationPresent(DenyAll.class)
                || as.isAnnotationPresent(RolesAllowed.class)
                || as.isAnnotationPresent(PermitAll.class)
                || as.isAnnotationPresent(DenyAll.class);

        if (hasAnnotation) {
            if (this.authFilter != null) {
                context.register(this.authFilter);
            } else {
                context.register(this.authFilterClass);
            }
        }
    }
}
