package com.pet.Better.service;

import com.pet.Better.model.*;
import com.pet.Better.repository.ClientRepository;
import com.pet.Better.repository.RegularTransactionRepository;
import com.pet.Better.repository.SupplierRepository;
import com.pet.Better.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RegularTransactionRepository regularTransactionRepository;

    public List<Transaction> filterTransactions(String category) {
        List<Transaction> filteredTransactions;
        if (category.equals("None")) {
            filteredTransactions = transactionRepository.findAll();
        } else {
            filteredTransactions = transactionRepository.findByCategory(category);
        }
        return filteredTransactions;
    }

    public List<Transaction> sortTransactions(String sortBy,List<Transaction> transactions){
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "date":
                    Collections.sort(transactions, Comparator.comparing(Transaction::getDate));
                    break;
                case "amount":
                    Collections.sort(transactions, Comparator.comparing(Transaction::getAmount));

            }
        }
        return transactions;
    }


        public void createTransaction(String client,
                                  String supplier,
                                  Long amount,
                                  String category,
                                  String description,
                                  Boolean regular,
                                  String dateTime) {
        if (Boolean.TRUE.equals(regular)){
            createRegularTransaction(client,
                    supplier,
                    amount,
                    category,
                    description,
                    LocalDateTime.parse(dateTime)
            );
        }
        else {
            createOneTimeTransaction(client,
                    supplier,
                    amount,
                    category,
                    description);
        }

    }

    private void createRegularTransaction(String client,
                                         String supplier,
                                         Long amount,
                                         String category,
                                         String description,
                                         LocalDateTime startDate) {
        RegularTransaction transaction = new RegularTransaction(amount, category, description, startDate);
        transaction.setDate(LocalDateTime.now());


        transaction.setClient(clientRepository.findByName(client));
        transaction.setSupplier(supplierRepository.findByName(supplier));

        transactionRepository.save(transaction);

    }

    @Scheduled(cron = "@daily")
    private void startRegularTransactions() {
        List<RegularTransaction> regularTransactions = regularTransactionRepository.findAll();

        for (RegularTransaction regularTransaction : regularTransactions) {
            if (LocalDateTime.now().isAfter(regularTransaction.getStartDate())) {
                createOneTimeTransaction(regularTransaction.getClient().getName(),
                        regularTransaction.getSupplier().getName(),
                        regularTransaction.getAmount(),
                        regularTransaction.getCategory(),
                        regularTransaction.getDescription());
            }
        }
    }

    private void createOneTimeTransaction(String client,
                                         String supplier,
                                         Long amount,
                                         String category,
                                         String description) {
        OneTimeTransaction transaction = new OneTimeTransaction(amount, category, description);
        transaction.setDate(LocalDateTime.now());

        transaction.setClient(clientRepository.findByName(client));
        transaction.setSupplier(supplierRepository.findByName(supplier));
        transactionRepository.save(transaction);
    }

    public void deleteOneTransaction(Long id) {
        transactionRepository.delete(transactionRepository.getById(id));
    }

    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
