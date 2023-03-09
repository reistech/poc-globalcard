package com.globalcards.adapter.driver.resource;

import io.smallrye.health.checks.UrlHealthCheck;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class CustomHealthCheck {

    @Liveness
    HealthCheck checkURL() {
        return new UrlHealthCheck("http://localhost:60603/")
                .name("ExternalURL health check").requestMethod(HttpMethod.GET).statusCode(200);
    }
}
