package com.pet.Better.model;


import javax.persistence.*;

@Entity
public class OneTimeTransaction extends Transaction{

    public OneTimeTransaction(Long amount, String category, String description) {
        super(amount, category, description);
    }

    public OneTimeTransaction() {
    }
}
