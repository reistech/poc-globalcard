package com.globalcards.adapter.infrastructure.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Cacheable
public class InvoiceType extends PanacheEntity implements Serializable {

    @Column( unique = true)
    public String name;

}
