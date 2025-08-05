package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesController {

    @GetMapping("/resources")
    public String getResources(Model model) {
        // Add any relevant data to the model here if needed
        return "resources"; // Corresponding HTML page
    }
}
