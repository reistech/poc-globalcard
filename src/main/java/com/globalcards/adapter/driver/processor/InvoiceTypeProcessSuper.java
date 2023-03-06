package com.globalcards.adapter.driver.processor;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.rabbitmq.IncomingRabbitMQMetadata;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;

import java.net.SocketException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class InvoiceTypeProcessSuper {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceTypeProcessor.class);

    public CompletionStage<Void> process(Message<String> incomingMessage) {
        Message<String> outgoingMessage = null;
        try {
            LOG.info("[VhostRouter] sending... ");
            outgoingMessage = Message.of(incomingMessage.getPayload(),Metadata.of(fillMetadata(incomingMessage)),incomingMessage::ack);
        } catch (Exception e) {
            LOG.error("[ VhostRouter] erro ao emitir mensagem ao destino", e);
            incomingMessage.nack(e);
        }
        return outgoingMessage.ack();
    }

    OutgoingRabbitMQMetadata fillMetadata(Message<String> message){
        OutgoingRabbitMQMetadata metadata = null;
        try {
            Optional<IncomingRabbitMQMetadata> optionalIncomingRabbitMQMetadata = message.getMetadata(IncomingRabbitMQMetadata.class);
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

         throw new SocketException("testes");
        } catch (Exception e) {
            LOG.error("[ VhostRouter] erro ao emitir mensagem ao destino");
            message.nack(e);
        }
        return metadata;
    }
}
