package myblog.auth;

import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
        Class<?> cls = resourceInfo.getResourceClass();
        Method method = resourceInfo.getResourceMethod();

        if (!cls.isAnnotationPresent(PermitAll.class)
                && !method.isAnnotationPresent(PermitAll.class)) {
            if (this.authFilter != null) {
                context.register(this.authFilter);
            } else {
                context.register(this.authFilterClass);
            }
        }
    }
}
