package com.pet.Better.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "accountant")
public class Accountant {

    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @ElementCollection(targetClass = ROLE.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "accountant_roles", joinColumns = @JoinColumn(name = "accountant_name"))
    @Enumerated(EnumType.STRING)
    private Set<ROLE> roles;


    @Column
    private String resetPasswordToken;

    @Column
    private boolean active;


//todo добавити поле для активності
//(якщо користувач не заходив певний час - видалити його)


    public Accountant(){}

    public Accountant(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
