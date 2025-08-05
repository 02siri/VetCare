package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.controllers.PrescriptionController;
import au.edu.rmit.sept.webapp.services.CartService;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PrescriptionController.class)
public class PrescriptionPageCategoryFilterTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    public void testFilterByCategory_Allergy() throws Exception {
        mockMvc.perform(get("/prescription")
                .param("category", "allergy")) // "allergy" category as a parameter
                .andExpect(status().isOk()) // Verify the status
                .andExpect(view().name("prescription")) // Ensure returned is "prescription"
                .andExpect(model().attribute("products", hasItem(
                        hasProperty("medication", is("Apoquel (Oclacitinib)")) // Check that Apoquel is included
                )));
    }

    @Test
    public void testFilterByCategory_AntiInflammatory() throws Exception {
        mockMvc.perform(get("/prescription")
                .param("category", "anti-inflammatory")) // "anti-inflammatory" category as a parameter
                .andExpect(status().isOk()) // Verify the status
                .andExpect(view().name("prescription")) // Ensure returned is "prescription"
                .andExpect(model().attribute("products", hasItem(
                        hasProperty("medication", is("Metacam (Meloxicam)")) // Check that Metacam is included
                )));
    }

    @Test
    public void testFilterByCategory_Antibiotics() throws Exception {
        mockMvc.perform(get("/prescription")
                .param("category", "antibiotics")) // "antibiotics" category as a parameter
                .andExpect(status().isOk()) // Verify the status
                .andExpect(view().name("prescription")) // Ensure returned is "prescription"
                .andExpect(model().attribute("products", hasItem(
                        hasProperty("medication", is("Clavamox (Amoxicillin)")) // Check that Clavamox is included
                )))
                .andExpect(model().attribute("products", hasItem(
                        hasProperty("medication", is("Baytril (Enrofloxacin)")) // Check that Baytril is included
                )));
    }

    @Test
    public void testFilterByCategory_Behavioral() throws Exception {
        mockMvc.perform(get("/prescription")
                .param("category", "behavioral")) // "behavioral" category as a parameter
                .andExpect(status().isOk()) // Verify the status
                .andExpect(view().name("prescription")) // Ensure returned is "prescription"
                .andExpect(model().attribute("products", hasItem(
                        hasProperty("medication", is("Prozac (Fluoxetine)")) // Check that Prozac is included
                )));
    }

    @Test
    public void testFilterByCategory_Gastrointestinal() throws Exception {
        mockMvc.perform(get("/prescription")
                .param("category", "gastrointestinal")) // "gastrointestinal" category as a parameter
                .andExpect(status().isOk()) // Verify the status
                .andExpect(view().name("prescription")) // Ensure returned is "prescription"
                .andExpect(model().attribute("products", hasItem(
                        hasProperty("medication", is("Cerenia (Maropitant)")) // Check that Cerenia is included
                )));
    }

}
