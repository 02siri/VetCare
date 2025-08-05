// src/main/java/au/edu/rmit/sept/webapp/controller/HomeController.java
package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prescription1 {

    @GetMapping("/Prescription1")
    public String Prescription1() {
        return "Prescription1"; // Returns the home.html view
    }
}
