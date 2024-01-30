package com.pet.Better.controllers;

import com.pet.Better.model.*;
import com.pet.Better.service.ClientService;
import com.pet.Better.service.SupplierService;
import com.pet.Better.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/home/acc/transaction")
public class AccController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientService clientService;

    @Autowired
    SupplierService supplierService;

    @GetMapping()
    public String transaction(Model model) {

        List<Transaction> transactions = transactionService
                .getAllTransactions()
                .stream()
                .filter(OneTimeTransaction.class::isInstance)
                .toList();
        if (transactions.isEmpty()){
            model.addAttribute("transaction","you don`t have any transactions");
        }
        model.addAttribute("transactions", transactions);
        return "transaction_form";
    }

    @PostMapping()
    public String filterTransaction(@RequestParam(required = false) String category,
                                    @RequestParam(required = false) String sortBy,
                                    Model model) {
        List<Transaction> transactions = transactionService.filterTransactions(category);

        transactions = transactionService.sortTransactions(sortBy,transactions);
        model.addAttribute("transactions", transactions);
        return "transaction_form";
    }


    @GetMapping("/add")
    public String transactionAdd(Model model) {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        List<Client> clients = clientService.getAllClients();

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("clients", clients);
        return "add_form";
    }

    @PostMapping("/add")
    public String procTransactionAdd(Model model,
                                     @RequestParam String client,
                                     @RequestParam String supplier,
                                     @RequestParam Long amount,
                                     @RequestParam String category,
                                     @RequestParam String description,
                                     @RequestParam(name = "regular", required = false) Boolean regular,
                                     @RequestParam String dateTime) {

        transactionService.createTransaction(client, supplier, amount, category, description, regular, dateTime);
        return "redirect:/home/acc/transaction";
    }

    @GetMapping("/delete/{id}")
    public String transactionDeleteOne(@PathVariable Long id, Model model) {
        transactionService.deleteOneTransaction(id);
        return "redirect:/home/acc/transaction";
    }

    @GetMapping("/delete")
    public String transactionDeleteAll(Model model) {
        transactionService.deleteAllTransactions();
        return "redirect:/home/acc/transaction";
    }
}
