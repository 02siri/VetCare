package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.CartService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest({ HomeController.class, ResourcesController.class, Article1Controller.class, Article2Controller.class,
        Article3Controller.class, CartController.class, MedicalRecordController.class, AppointmentController.class,
        PrescriptionController.class, Prescription1.class, Prescription2.class, Prescription3.class,
        Prescription4.class, Prescription5.class, Prescription6.class
})
public class NavigationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private CartService cartService;

    @Test
    public void testHomePageNavigation() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home")); // Ensure this matches the view name returned by HomeController
    }

    // Test navigation to appointment page
    @Test
    public void testAppointmentPageNavigation() throws Exception {
        mockMvc.perform(get("/appointment"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment"));
    }

    // Test navigation to medical record page
    @Test
    public void testMedicalRecordPageNavigation() throws Exception {
        mockMvc.perform(get("/medical-record"))
                .andExpect(status().isOk())
                .andExpect(view().name("medical_record"));
    }

    // Test navigation to Prescription page
    @Test
    public void testPrescriptionPageNavigation() throws Exception {
        mockMvc.perform(get("/prescription"))
                .andExpect(status().isOk())
                .andExpect(view().name("prescription"));
    }

    // Test navigation to Resource page
    @Test
    public void testResourcePageNavigation() throws Exception {
        mockMvc.perform(get("/resources"))
                .andExpect(status().isOk())
                .andExpect(view().name("resources"));
    }

    // Test navigation to cart page
    @Test
    public void testCartPageNavigation() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }

    @Test
    public void testArticle1PageNavigation() throws Exception {
        mockMvc.perform(get("/article1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Article1"));
    }

    @Test
    public void testArticle2PageNavigation() throws Exception {
        mockMvc.perform(get("/article2"))
                .andExpect(status().isOk())
                .andExpect(view().name("Article2"));
    }

    @Test
    public void testArticle3PageNavigation() throws Exception {
        mockMvc.perform(get("/article3"))
                .andExpect(status().isOk())
                .andExpect(view().name("Article3"));
    }

    @Test
    public void testPrescription1PageNavigation() throws Exception {
        mockMvc.perform(get("/Prescription1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Prescription1"));
    }

    @Test
    public void testPrescription2PageNavigation() throws Exception {
        mockMvc.perform(get("/Prescription2"))
                .andExpect(status().isOk())
                .andExpect(view().name("Prescription2"));
    }

    @Test
    public void testPrescription3PageNavigation() throws Exception {
        mockMvc.perform(get("/Prescription3"))
                .andExpect(status().isOk())
                .andExpect(view().name("Prescription3"));
    }

    @Test
    public void testPrescription4PageNavigation() throws Exception {
        mockMvc.perform(get("/Prescription4"))
                .andExpect(status().isOk())
                .andExpect(view().name("Prescription4"));
    }

    @Test
    public void testPrescription5PageNavigation() throws Exception {
        mockMvc.perform(get("/Prescription5"))
                .andExpect(status().isOk())
                .andExpect(view().name("Prescription5"));
    }

    @Test
    public void testPrescription6PageNavigation() throws Exception {
        mockMvc.perform(get("/Prescription6"))
                .andExpect(status().isOk())
                .andExpect(view().name("Prescription6"));
    }

}
