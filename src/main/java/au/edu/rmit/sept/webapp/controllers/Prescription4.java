// src/main/java/au/edu/rmit/sept/webapp/controller/HomeController.java
package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prescription4 {

    @GetMapping("/Prescription4")
    public String Prescription4() {
        return "Prescription4"; // Returns the home.html view
    }
}
