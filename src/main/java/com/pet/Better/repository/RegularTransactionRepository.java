package com.pet.Better.repository;

import com.pet.Better.model.RegularTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularTransactionRepository extends JpaRepository<RegularTransaction,Long> {
}
