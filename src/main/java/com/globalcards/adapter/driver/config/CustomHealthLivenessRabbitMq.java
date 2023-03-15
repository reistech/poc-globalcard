package com.globalcards.adapter.driver.config;

import com.rabbitmq.client.ConnectionFactory;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
@Liveness
public class CustomHealthLivenessRabbitMq implements HealthCheck {
    @Override
     public HealthCheckResponse call() {
        try {
            var namedOptions = NamedOptionsConfig.getNamedOptions();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername(namedOptions.getUser());
            factory.setPassword(namedOptions.getPassword());
            factory.setVirtualHost(namedOptions.getVirtualHost());
            factory.setHost(namedOptions.getHost());
            factory.setPort(namedOptions.getPort());
            factory.newConnection();
            
            return HealthCheckResponse.builder().up().name("Custom  Health Check RabbitMQ container  is ready  ").build();
        }
        catch (Exception exception)
        {
           return HealthCheckResponse.builder().down().name("Custom Health Check RabbitMQ container is not readyds").build();
        }
    }
}
