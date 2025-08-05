package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.services.ClinicLocatorService;
import au.edu.rmit.sept.webapp.models.Clinic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ClinicLocatorController {

    @Autowired
    private ClinicLocatorService clinicLocatorService;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @GetMapping("/clinic-locator")
    public String showClinicLocator(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "clinic-locator";
    }

    @PostMapping("/clinic-locator")
    public String findClinics(@RequestParam("address") String address, 
                              @RequestParam("postcode") String postcode, 
                              Model model) {
        try {
            Map<String, Object> result = clinicLocatorService.findNearbyClinics(address);
            
            @SuppressWarnings("unchecked")
            List<Clinic> nearbyClinics = (List<Clinic>) result.get("clinics");
            
            @SuppressWarnings("unchecked")
            Map<String, Double> userLocation = (Map<String, Double>) result.get("userLocation");

            model.addAttribute("address", address);
            model.addAttribute("postcode", postcode);
            model.addAttribute("clinics", nearbyClinics);
            model.addAttribute("userLocation", userLocation);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while finding nearby clinics: " + e.getMessage());
        }
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "clinic-locator";
    }
}