package com.globalcards.domain.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Cacheable;
import java.io.Serializable;


@Getter
@Setter
@Cacheable
public class InvoiceType implements Serializable {

    public String name;

    public InvoiceType (String name){
        this.name = name;
    }
}
