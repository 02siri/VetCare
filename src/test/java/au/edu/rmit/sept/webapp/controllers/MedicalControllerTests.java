package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.MedicalRecord;
import au.edu.rmit.sept.webapp.models.User;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MedicalControllerTests {

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Mock
    private Model model;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMedicalRecord_UserIsAdmin() {
        User adminUser = new User();
        adminUser.setUsername("admin");


        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser); // User is an admin


        String viewName = medicalRecordController.getMedicalRecord(model, request);


        ArgumentCaptor<MedicalRecord> medicalRecordCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(model).addAttribute(eq("record"), medicalRecordCaptor.capture());
        verify(model).addAttribute("isAdmin", true);

        MedicalRecord capturedRecord = medicalRecordCaptor.getValue();
        assertEquals("Medical Record", capturedRecord.getTitle());
        assertEquals("Good", capturedRecord.getHealthCondition());
        assertEquals("David", capturedRecord.getVeterinarian());
        assertEquals("abc", capturedRecord.getNotes());
        assertEquals("Vaccination", capturedRecord.getVaccinationType());
        assertEquals("17/08/2024", capturedRecord.getVaccinationDate());
        assertEquals("16/12/2024", capturedRecord.getDueDate());

        assertEquals("medical_record", viewName);
    }


    @Test
    void testGetMedicalRecord_UserIsNotAdmin() {

        User normalUser = new User();
        normalUser.setUsername("user");


        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(normalUser); // User is not an admin


        String viewName = medicalRecordController.getMedicalRecord(model, request);


        verify(model).addAttribute("isAdmin", false);
        assertEquals("medical_record", viewName);
    }

    @Test
    void testGetMedicalRecord_NoUser() {

        when(request.getAttribute("user")).thenReturn(null);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null); // No user in session

        String viewName = medicalRecordController.getMedicalRecord(model, request);


        verify(model).addAttribute("isAdmin", false);
        assertEquals("medical_record", viewName);
    }

}
