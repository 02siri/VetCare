// src/main/java/au/edu/rmit/sept/webapp/controller/HomeController.java
package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Returns the home.html view
    }
}
