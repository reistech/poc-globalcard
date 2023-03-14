package com.globalcards.adapter.driver.config;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
@Liveness
public class CustomHealthLivenessRabbitMq implements HealthCheck {
    @Channel("sends")
    Emitter<String> invoyceTypeRequestEmitter;
    
    @Override
     public HealthCheckResponse call() {
        try {
            var namedOptions = NamedOptionsConfig.getNamedOptions();
            var namedJson  = NamedOptionsConfig.getNamedOptions().toJson();
    
            invoyceTypeRequestEmitter.send("healthcheck");
            return HealthCheckResponse.builder().up().name("RabbitMQ container  is ready  ").build();
         }
            catch (Exception exception)
        {
              return HealthCheckResponse.builder().down().name("RabbitMQ container is not readyds").build();
         }
    }
    
}
