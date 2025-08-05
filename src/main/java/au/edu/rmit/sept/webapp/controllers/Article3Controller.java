package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Article3Controller {

    @GetMapping("/article3")
    public String article3() {
        return "Article3";
    }
}
