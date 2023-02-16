package com.globalcards.adapter.driver.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class InvoiceTypeProcessorRequest {

    public int id;
    public String  name;
    public InvoiceTypeProcessorRequest() { }

    public InvoiceTypeProcessorRequest(int id, String name) {
        this.id = id;
        this.name = name ;
    }


}
