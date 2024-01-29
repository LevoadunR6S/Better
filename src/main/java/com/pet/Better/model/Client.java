package com.pet.Better.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Client {

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

    @OneToMany(mappedBy = "client")
    private List<OneTimeTransaction> transactions;

    public Client(String name,
                  String number,
                  String email,
                  String address) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
    }

    public Client() {
    }
}
