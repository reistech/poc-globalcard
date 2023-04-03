package com.globalcards.adapter.driver.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.eclipse.microprofile.health.*;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
@Liveness
@Readiness
@Startup
public class CustomHealthLivenessRabbitMq implements HealthCheck {
    Connection connection;
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
             connection=  factory.newConnection();
            return HealthCheckResponse.builder().up().name("Custom  Health Check RabbitMQ container  is ready  ").build();
        }
        catch (Exception exception)
        {
           return HealthCheckResponse.builder().down().name("Custom Health Check RabbitMQ container is not ready").build();
        } finally {
            try { connection.close(); } catch (Exception e) {  }
        }
    }
}
