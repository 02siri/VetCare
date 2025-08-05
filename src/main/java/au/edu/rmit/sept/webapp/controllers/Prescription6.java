// src/main/java/au/edu/rmit/sept/webapp/controller/HomeController.java
package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prescription6 {

    @GetMapping("/Prescription6")
    public String Prescription6() {
        return "Prescription6"; // Returns the home.html view
    }
}
