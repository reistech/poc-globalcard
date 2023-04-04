package com.globalcards.adapter.driver.controller;

import com.globalcards.adapter.infrastructure.entity.InvoiceType;
import com.globalcards.domain.port.IInvoiceTypeService;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Path("invoice-type")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Slf4j
public class InvoiceTypeController {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceTypeController.class);
    
    @Channel("sends")
    Emitter<String> invoyceTypeRequestEmitter;
    
    @Channel("contract-output")
    Emitter<String> contractOutPutEmitter;
    
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
    @Retry
    public String  emit() {
        LOG.info("Trying Post [TimeStamp]  " + LocalDateTime.now());
        String response = "Não existe arquivo de texto";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/lucas.barbosa.p.reis/test.txt"));
            String line = bufferedReader.readLine();
            while( line != null ) {
                contractOutPutEmitter.send(line);
                line = bufferedReader.readLine();
                response = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
