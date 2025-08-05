// src/main/java/au/edu/rmit/sept/webapp/controller/HomeController.java
package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prescription5 {

    @GetMapping("/Prescription5")
    public String Prescription5() {
        return "Prescription5"; // Returns the home.html view
    }
}
