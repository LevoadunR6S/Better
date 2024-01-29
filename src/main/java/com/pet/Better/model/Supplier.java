package com.pet.Better.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column
    private String number;

    @Column
    private String email;

    @Column
    private String address;

    @OneToMany(mappedBy = "supplier")
    private List<OneTimeTransaction> transactions;
}
