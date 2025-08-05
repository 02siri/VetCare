package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Article2Controller {

    @GetMapping("/article2")
    public String article2() {
        return "Article2";
    }
}
