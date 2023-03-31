package com.globalcards.adapter.driver.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class DLQProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DLQProcessor.class);
    
     @Retry(maxRetries = 4)
     @Incoming("contract-dead-letter-queue")
     @Outgoing("fila-output")
     @Acknowledgment(Acknowledgment.Strategy.MANUAL)
     public Message<String> process(Message<String> incomingMessage) throws RuntimeException {
        Message<String> outgoingMessage = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new JavaTimeModule());
//            BinResponse bin = mapper.readValue(incomingMessage.getPayload(), BinResponse.class);
//
//            LOG.info("[Sending message] " + bin.toString());
            LOG.info("[TimeStamp] " + LocalDateTime.now());
            
            if (new Random().nextBoolean()) {
                Thread.sleep(1000);
                throw new RuntimeException("[TrataRetryAndDLQ] Tratamento de Retries e Dead Letter Queue");
                
            }
            outgoingMessage = Message.of(incomingMessage.getPayload(), Metadata.of(fillMetadata(incomingMessage)), incomingMessage::ack);
        } catch(Exception e)
        {
            LOG.error(e.getMessage());
            incomingMessage.nack(e);
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
