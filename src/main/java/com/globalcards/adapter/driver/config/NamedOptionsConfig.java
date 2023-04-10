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
                    .setPort(5672)
                    .setNetworkRecoveryInterval(500000)
                    .setReconnectInterval(500000);
    }
}
