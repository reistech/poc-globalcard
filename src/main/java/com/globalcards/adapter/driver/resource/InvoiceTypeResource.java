package com.globalcards.adapter.driver.resource;

import com.globalcards.adapter.infrastructure.entity.InvoiceType;
import com.globalcards.domain.port.IInvoiceTypeService;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


@Path("invoice-type")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Slf4j
public class InvoiceTypeResource {

    @Channel("sends")
    Emitter<String> invoyceTypeRequestEmitter;

    @Inject
    IInvoiceTypeService invoiceTypeService;

    @GET
    public Uni<List<InvoiceType>> get() {
        return invoiceTypeService.getAll();
    }

    @GET
    @Path("{id}")
    public Uni<InvoiceType> getById(Long id) {
        return invoiceTypeService.getById(id);
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(Long id, InvoiceType invoiceType) {
        return invoiceTypeService.update(id, invoiceType );
    }

    @POST
    public Uni<Response> create(InvoiceType invoiceType) {
        return invoiceTypeService.save(invoiceType);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return invoiceTypeService.delete(id);
    }


    @POST
    @Path("/emit")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest(InvoiceType invoiceType) {
        invoyceTypeRequestEmitter.send(invoiceType.name);
        return invoiceType.name;
    }

}
