package it.gp.springsec3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankAccountController {

    @GetMapping("/account.html")
    public String getAccount(){
        return "account";
    }

    @GetMapping("/cards.html")
    public String getCards() {
        return "cards";
    }

    @GetMapping("loans.html")
    public String getLoans() {
        return "loans";
    }

    @GetMapping("balance.html")
    public String getBalance() {
        return "balance";
    }
}
