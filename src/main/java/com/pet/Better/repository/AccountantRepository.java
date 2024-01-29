package com.pet.Better.repository;

import com.pet.Better.model.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountantRepository extends JpaRepository<Accountant,String> {

    Accountant findByUsername(String username);

    @Query("SELECT a FROM Accountant a WHERE a.email = ?1")
    Accountant findByEmail(String email);

    Accountant findByResetPasswordToken(String token);

}
