package com.pet.Better.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    Long amount;

    @Column
    String category;

    @Column
     LocalDateTime date;

    @Column
     String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Transaction(Long amount,
                              String category,
                              String description) {
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public Transaction() {
    }
}
