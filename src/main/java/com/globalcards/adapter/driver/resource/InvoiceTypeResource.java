package com.globalcards.adapter.driver.resource;

import com.globalcards.adapter.driver.processor.RetryAndDLQProcessor;
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
import java.util.UUID;


@Path("invoice-type")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Slf4j
public class InvoiceTypeResource {
<<<<<<< HEAD

//    @Channel("sends")
//    Emitter<String> invoyceTypeRequestEmitter;

=======
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceTypeResource.class);
    
    @Channel("sends")
    Emitter<String> invoyceTypeRequestEmitter;
    
    @Channel("contract-output")
    Emitter<String> contractOutPutEmitter;
    
    
>>>>>>> feat/simulacao-health-check
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
<<<<<<< HEA


//    @POST
//    @Path("/emit")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String createRequest(InvoiceType invoiceType) {
//        invoyceTypeRequestEmitter.send(invoiceType.name);
//        return invoiceType.name;
//    }
=======
    
    
    
    @POST
    @Path("/emit")
    @Produces(MediaType.TEXT_PLAIN)
    @Retry
    public String createRequest() {
    
    
        LOG.info("Trying Post [TimeStamp]  " + LocalDateTime.now());
    
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/lucas.barbosa.p.reis/test.txt"));
            String line = bufferedReader.readLine();
            while( line != null ) {
                contractOutPutEmitter.send(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            
        }
        
        
//            throw new RuntimeException("[TrataRetryAndDLQ] Tratamento de Retries e Dead Letter Queue");

        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
>>>>>>> feat/simulacao-health-check

}
