package com.pet.Better.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
public class RegularTransaction extends Transaction{

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    public RegularTransaction() {
    }

    public RegularTransaction(Long amount, String category, String description, LocalDateTime startDate) {
        super(amount, category, description);
        this.startDate = startDate;
    }
}