package com.globalcards.adapter.driver.processor;


import com.globalcards.adapter.infrastructure.entity.InvoiceType;
import com.globalcards.domain.port.IInvoiceTypeService;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


@ApplicationScoped
public class InvoiceTypeProcessor extends InvoiceTypeProcessSuper {

    @Inject
    IInvoiceTypeService invoiceTypeService;

    @Incoming("requests")
    public CompletionStage<Void> process(Message<String> message) {

        Log.info("Mensagem recebida " + message.getPayload());
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setName(message.getPayload());

        this.invoiceTypeService.save(invoiceType);
        super.process(message);
        return      message.ack();
    }
}

