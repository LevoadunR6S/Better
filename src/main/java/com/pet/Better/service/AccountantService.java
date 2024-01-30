package com.pet.Better.service;

import com.pet.Better.model.ROLE;
import com.pet.Better.model.Accountant;
import com.pet.Better.repository.AccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class AccountantService {

    @Autowired
    AccountantRepository accountantRepository;

    public void createAccountant(Accountant accountant,
                                 String token){
        accountant.setResetPasswordToken(token);
        accountantRepository.save(accountant);
    }


    public void createAccountant(Accountant accountant){
        accountant.setRoles(Collections.singleton(ROLE.ACCOUNTANT));
        accountant.setActive(true);
        accountantRepository.save(accountant);
    }

    public Accountant getByUsername(String username){
        return accountantRepository.findByUsername(username);
    }

    public Accountant getByEmail(String email){
    return accountantRepository.findByEmail(email);
    }

    public Accountant getByToken(String token){
        return accountantRepository.findByResetPasswordToken(token);
    }

}
