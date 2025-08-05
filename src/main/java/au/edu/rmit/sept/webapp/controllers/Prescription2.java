// src/main/java/au/edu/rmit/sept/webapp/controller/HomeController.java
package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prescription2 {

    @GetMapping("/Prescription2")
    public String Prescription2() {
        return "Prescription2"; // Returns the home.html view
    }
}
