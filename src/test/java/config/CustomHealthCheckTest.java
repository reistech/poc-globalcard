package config;

import com.globalcards.adapter.driver.config.CustomHealthLivenessRabbitMq;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomHealthCheckTest {
    @Inject
    CustomHealthLivenessRabbitMq customHealthCheck;
    HealthCheckResponse healthUp = HealthCheckResponse.builder().up().name("CustomHealthCheck").build();
    HealthCheckResponse healthDown = HealthCheckResponse.builder().down().name("CustomHealthCheck").build();
    @Test
    @Order(1)
    void shouldHealthCheckStatusUpTest() {
         HealthCheckResponse response = customHealthCheck.call();
         Assertions.assertEquals(healthUp.getStatus(),response.getStatus());
    }
    @Test
    @Order(2)
    void shouldHealthCheckStatusDownTest() {
          HealthCheckResponse response = customHealthCheck.call();
         Assertions.assertEquals(healthDown.getStatus(),response.getStatus());
    }
}