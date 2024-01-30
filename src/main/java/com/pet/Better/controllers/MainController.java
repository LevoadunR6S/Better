package com.pet.Better.controllers;

import com.pet.Better.model.Accountant;
import com.pet.Better.model.RegularTransaction;
import com.pet.Better.model.Transaction;
import com.pet.Better.service.AccountantService;
import com.pet.Better.service.MailService;
import com.pet.Better.service.TransactionService;
import com.pet.Better.service.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class MainController {



    @Autowired
    AccountantService accountantService;

    @Autowired
    MailService mailService;

    @Autowired
    TransactionService transactionService;


    @GetMapping("/signup")
    public String signup() {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String procSignup(Model model, @RequestParam String cPassword, Accountant accountant) {
        if (accountantService.getByUsername(accountant.getUsername()) != null) {
            model.addAttribute("error", "User exists");
            return "signup_form";
        } else if (!(accountant.getPassword().equals(cPassword))) {
            model.addAttribute("error", "Passwords don't match");
            return "signup_form";
        }

        accountantService.createAccountant(accountant);
        return "redirect:/login";
    }


    @GetMapping("/home/acc")
    public String account() {
        return "account";
    }

    @GetMapping("/home/acc/info")
    public String showStatistic(Model model){
        List<Transaction> transactions = transactionService.getAllTransactions();
        Long sum = 0L;
        for (Transaction transaction: transactions){
            sum+=transaction.getAmount();
        }
        model.addAttribute("sum", sum);
        model.addAttribute("count", transactions.size());
        model.addAttribute("avg", sum/transactions.size());
        model.addAttribute("regularCount",transactions.stream().filter(RegularTransaction.class::isInstance).toList().size());
        return "info";
    }

    @GetMapping("/forgot")
    public String forgot(Model model) {
        return "forgot";
    }

    @PostMapping("/forgot")
    public String procForgot(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        Accountant accountant = null;

        if (email != null) {
            if (email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
                accountant = accountantService.getByEmail(email);
            } else {
                model.addAttribute("error", "email isn't correct");
                return "forgot";
            }
        }

        if (accountant != null) {
            accountantService.createAccountant(accountant,token);
            String link = Utility.getSiteURL(request) + "/reset?token=" + token;
            mailService.sendEmail(email, link);
            return "redirect:/signup";
        } else {
            model.addAttribute("error", "incorrect input, please try again");
            return "forgot";
        }
    }


    @GetMapping("/reset")
    public String reset(@Param(value = "token") String token, Model model) {

        Accountant accountant = accountantService.getByToken(token);

        if (accountant == null) {
            model.addAttribute("message", "Invalid Token");
            return "reset";
        }
        model.addAttribute("token", token);

        return "reset";
    }

    @PostMapping("/reset")
    public String procReset(@RequestParam String token,
                            @RequestParam String password,
                            @RequestParam String cPassword,
                            Model model) {

        Accountant accountant = accountantService.getByToken(token);

        if (accountant == null) {
            model.addAttribute("error", "Invalid reset token");
            return "reset";
        }

        if (password == null || password.isEmpty() || !password.equals(cPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "reset";
        }

        accountant.setPassword(password);
        accountantService.createAccountant(accountant,token);
        return "redirect:/login";
    }

}
