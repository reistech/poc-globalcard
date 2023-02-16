package com.globalcards.adapter.driver.processor;


import com.globalcards.domain.port.IInvoiceTypeService;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


@ApplicationScoped
public class InvoiceTypeProcessor {

    @Inject
    IInvoiceTypeService invoiceTypeService;

    @Incoming("requests")
    @Blocking
    public CompletionStage<Void> consume(Message<String> message) {
        Log.info("Mensagem recebido " + message.toString());

       String string = message.toString();
       String payload= message.getPayload();

        Log.info("Mensagem recebido " + message.getPayload());

        // Acknowledge the incoming message, marking the RabbitMQ message as `accepted`.
        return message.ack();
    }
}
