package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Article1Controller {

    @GetMapping("/article1")
    public String article1() {
        return "Article1";
    }
}
