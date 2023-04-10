package com.globalcards.adapter.driver.processor;

import io.smallrye.reactive.messaging.rabbitmq.IncomingRabbitMQMetadata;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class RetryProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(RetryProcessor.class);
     @Retry()
     @Incoming("fila-input")
     @Outgoing("fila-output")
     @Acknowledgment(Acknowledgment.Strategy.MANUAL)
     public Message<String> process(Message<String> incomingMessage) throws Exception {
        LOG.info("Trying incomming [TimeStamp] Retry  " + incomingMessage + LocalDateTime.now());
        Message<String> outgoingMessage = null;
        
        Exception e = new RuntimeException("[TrataRetryAndDLQ] Tratamento de Retries e Dead Letter Queue");
            outgoingMessage = Message.of(incomingMessage.getPayload(), Metadata.of(fillMetadata(incomingMessage)),incomingMessage::ack);
        if (new Random().nextBoolean()) {
            Thread.sleep(1000);
            incomingMessage.nack(e);
            throw  e;
        }
      return outgoingMessage;
    }

    OutgoingRabbitMQMetadata fillMetadata(Message<String> message) {
        Optional<IncomingRabbitMQMetadata> optionalIncomingRabbitMQMetadata = message.getMetadata(IncomingRabbitMQMetadata.class);
        OutgoingRabbitMQMetadata metadata = null;
        
        if (optionalIncomingRabbitMQMetadata.isPresent()) {
            IncomingRabbitMQMetadata incomingRabbitMQMetadata = optionalIncomingRabbitMQMetadata.get();
            String type = (String) incomingRabbitMQMetadata.getHeaders().get("type");
            metadata = new OutgoingRabbitMQMetadata.Builder()
                    .withHeader("type", type)
                    .withHeader("timestamp", incomingRabbitMQMetadata.getHeaders().get("timestamp"))
                    .withHeader("servicename", incomingRabbitMQMetadata.getHeaders().get("servicename"))
                    .withRoutingKey(type)
                    .build();
            
        }
        return metadata;
    }
}
