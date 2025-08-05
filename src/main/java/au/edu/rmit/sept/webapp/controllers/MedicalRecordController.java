package au.edu.rmit.sept.webapp.controllers;
import au.edu.rmit.sept.webapp.models.MedicalRecord;
import au.edu.rmit.sept.webapp.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import au.edu.rmit.sept.webapp.models.User;


import jakarta.servlet.http.HttpServletRequest;



@Controller
public class MedicalRecordController extends BaseController {

    @GetMapping("/medical-record")
    public String getMedicalRecord(Model model, HttpServletRequest request) {
        // Sample data for Medical Record
        MedicalRecord record = new MedicalRecord(
                "Medical Record",
                "Good",
                "David",
                "abc",
                "Vaccination",
                "17/08/2024",
                "16/12/2024"
        );
        // Add record to the model
        model.addAttribute("record", record);

        // Check if user is logged in and is admin
        User currentUser = getCurrentUser(request);
        boolean isAdmin = (currentUser != null && "admin".equals(currentUser.getUsername()));
        model.addAttribute("isAdmin", isAdmin);


        return "medical_record"; // Corresponding HTML page
    }
}

