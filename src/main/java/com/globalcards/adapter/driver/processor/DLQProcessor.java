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

@ApplicationScoped
public class DLQProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DLQProcessor.class);
    
     @Retry(maxRetries = 4)
     @Incoming("contract-dlq")
     @Outgoing("dlq-output")
     @Acknowledgment(Acknowledgment.Strategy.MANUAL)
     public Message<String> process(Message<String> incomingMessage){
        
        LOG.info("[TimeStamp] Trying incomming  DLQ " + LocalDateTime.now());
        Message<String> outgoingMessage = null;
        try
        {
            LOG.info("[TimeStamp] Trying incomming  " + LocalDateTime.now());
            
            outgoingMessage = Message.of(incomingMessage.getPayload(), Metadata.of(fillMetadata(incomingMessage)), incomingMessage::ack);
        } catch(Exception e)
        {
            LOG.error(e.getMessage());
            incomingMessage.nack(e);
        }
        
         incomingMessage.ack();
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
