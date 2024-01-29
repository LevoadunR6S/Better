package com.pet.Better.repository;

import com.pet.Better.model.Client;
import com.pet.Better.model.OneTimeTransaction;
import com.pet.Better.model.Supplier;
import com.pet.Better.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByClient(Client client);

    List<Transaction> findBySupplier(Supplier supplier);

    List<Transaction> findByCategory(String category);

}
