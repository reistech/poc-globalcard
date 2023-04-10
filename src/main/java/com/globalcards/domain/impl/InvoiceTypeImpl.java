package com.globalcards.domain.impl;

import com.globalcards.adapter.infrastructure.entity.InvoiceType;
import com.globalcards.adapter.infrastructure.repository.InvoiceTypeRepository;
import com.globalcards.domain.port.IInvoiceTypeService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

@ApplicationScoped
public class InvoiceTypeImpl implements IInvoiceTypeService {
    
//    @Channel("sends")
//    Emitter<String> invoyceTypeRequestEmitter;
//
//    @Channel("contract-output")
//    Emitter<String> contractOutPutEmitter;
    
    @Inject
    InvoiceTypeRepository invoiceTypeRepository;

    public Uni<Response> save(InvoiceType invoiceType) {

        if (invoiceType == null || invoiceType.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        System.out.println(invoiceType.name);

        var retorno = Panache.withTransaction(invoiceType::persist)
                .replaceWith(Response.ok(invoiceType).status(CREATED)::build);
        return retorno;
    }

    public Uni<Response> update(Long id, InvoiceType invoiceType) {

      return  Panache
              .withTransaction(() -> InvoiceType.<InvoiceType> findById(id)
                      .onItem().ifNotNull().invoke(entity -> entity.name = invoiceType.name)
              )
              .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
              .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }
    

//    public String emit() {
//
//        String response = "Não existe arquivo de texto";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/lucas.barbosa.p.reis/test.txt"));
//            String line = bufferedReader.readLine();
//            while( line != null ) {
//                contractOutPutEmitter.send(line);
//                line = bufferedReader.readLine();
//                response = line;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        return Response.ok().build();
//    }
    
    public Uni<Response> delete(final Long id) {

        return Panache.withTransaction(() -> InvoiceType.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(NO_CONTENT).build()
                        : Response.ok().status(NOT_FOUND).build());
    }

    public Uni<InvoiceType> getById(final Long id) {
        return this.invoiceTypeRepository.findById(id);
    }

    public Uni<List<InvoiceType>> getAll() {
        return this.invoiceTypeRepository.listAll(Sort.by("name"));
    }

}
