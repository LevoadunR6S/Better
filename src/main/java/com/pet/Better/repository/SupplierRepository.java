package com.pet.Better.repository;

import com.pet.Better.model.Client;
import com.pet.Better.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    Supplier findByName(String name);

    Supplier getById(Long id);
}
