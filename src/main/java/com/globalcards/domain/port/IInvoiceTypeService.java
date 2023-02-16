package com.globalcards.domain.port;

import com.globalcards.adapter.infrastructure.entity.InvoiceType;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.core.Response;
import java.util.List;


public interface IInvoiceTypeService {
    Uni<Response> save(final InvoiceType invoiceType);

    Uni<Response> delete(final Long id);

    Uni<InvoiceType> getById(final Long id);

    Uni<List<InvoiceType>> getAll();

    Uni<Response> update(Long Id, InvoiceType invoiceType);

}
