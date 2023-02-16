package com.globalcards.adapter.infrastructure.repository;

import com.globalcards.adapter.infrastructure.entity.InvoiceType;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class InvoiceTypeRepository implements PanacheRepository<InvoiceType> {

}
