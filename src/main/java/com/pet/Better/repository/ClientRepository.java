package com.pet.Better.repository;

import com.pet.Better.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Client findByName(String name);

    Client getById(Long id);
}
