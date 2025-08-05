// src/main/java/au/edu/rmit/sept/webapp/controller/HomeController.java
package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prescription3 {

    @GetMapping("/Prescription3")
    public String Prescription3() {
        return "Prescription3"; // Returns the home.html view
    }
}
