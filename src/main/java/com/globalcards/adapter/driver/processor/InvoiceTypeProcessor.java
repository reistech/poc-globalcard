package com.globalcards.adapter.driver.processor;


import com.globalcards.domain.port.IInvoiceTypeService;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class InvoiceTypeProcessor {
    @Inject
    IInvoiceTypeService invoiceTypeService;
    @Incoming("requests")
    Uni<Void> process(Message<String> message) {

        return  this.invoiceTypeService.saveMessage(message);
   }

}

