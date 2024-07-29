package it.gp.springsec3.controller;

import it.gp.springsec3.repository.CustomerReository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @Autowired
    private CustomerReository customerReository;

    @GetMapping(path = {"/index.html", "/", "/index"})
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/user_page.html")
    public String getUserPage() {
        return "user_page.html";
    }

    @GetMapping("/admin_page.html")
    public String getAdminPage() {
        return "admin_page.html";
    }
}
