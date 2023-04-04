package com.globalcards.adapter.driver.config;

import io.smallrye.common.annotation.Identifier;
import io.vertx.rabbitmq.RabbitMQOptions;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

@ApplicationScoped
public class NamedOptionsConfig {
    @Produces
    @Identifier("options")
    public static RabbitMQOptions getNamedOptions() {
            return new RabbitMQOptions()
//                    .setTrustAll(true)
//                    .setSsl(true)
//                    .setAutomaticRecoveryEnabled(true)
//                    .setAutomaticRecoveryOnInitialConnection(false)
                    .setPort(5672)
                    .setReconnectInterval(500000);
    }
}
