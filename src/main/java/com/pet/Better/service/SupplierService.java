package com.pet.Better.service;

import com.pet.Better.model.Supplier;
import com.pet.Better.repository.ClientRepository;
import com.pet.Better.repository.SupplierRepository;
import com.pet.Better.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }
}
